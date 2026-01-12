package io.github.mbp16.dishwidget.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.mbp16.dishwidget.activity.mainactivityviews.InitialSettingsView
import io.github.mbp16.dishwidget.activity.mainactivityviews.MealView
import io.github.mbp16.dishwidget.activity.mainactivityviews.UpdateView
import io.github.mbp16.dishwidget.activity.settingsactivityviews.GetMealSettingDataStore
import io.github.mbp16.dishwidget.activity.settingsactivityviews.GetMealSettingDataStore.Companion.mealDataStore
import io.github.mbp16.dishwidget.activity.settingsactivityviews.MainActivitySettingDataStore
import io.github.mbp16.dishwidget.activity.settingsactivityviews.MainActivitySettingDataStore.Companion.mainSettingsDatastore
import io.github.mbp16.dishwidget.ui.theme.DishWidgetTheme
import io.github.mbp16.dishwidget.utils.Release
import io.github.mbp16.dishwidget.utils.getUpdate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataFirmed = remember { mutableStateOf("") }
            val dialogViewing = remember { mutableStateOf(false) }
            var result: Any = false
            LaunchedEffect(Unit) {
                mealDataStore.data.collect { preferences ->
                    dataFirmed.value = if ((preferences[GetMealSettingDataStore.schoolIdLink] ?: "") != "" ||
                            (preferences[GetMealSettingDataStore.neisSchoolCode] ?: "") != "") "true" else "false"
                }
            }
            LaunchedEffect(Unit) {
                mainSettingsDatastore.data.collect { preferences ->
                    val updateAuto = preferences[MainActivitySettingDataStore.updateAuto] ?: true
                    if (updateAuto) {
                        val thread = Thread() {
                            result = getUpdate(this@MainActivity)
                            if (result != false) {dialogViewing.value = true}
                        }
                        thread.setUncaughtExceptionHandler { _, _ -> dialogViewing.value = false }
                        thread.start()
                    }
                }
            }
            DishWidgetTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (dialogViewing.value) {
                        UpdateView(dialogViewing, result as Release, this)
                    }
                    if (dataFirmed.value === "true") {
                        MealView(this, mainSettingsDatastore)
                    } else if (dataFirmed.value === "false") {
                        InitialSettingsView(this, mealDataStore, dataFirmed)
                    }
                }
            }
        }
    }
}
