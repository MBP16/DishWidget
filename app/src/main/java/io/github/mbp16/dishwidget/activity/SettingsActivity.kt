package io.github.mbp16.dishwidget.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.mbp16.dishwidget.activity.settingsactivityviews.AppInfoView
import io.github.mbp16.dishwidget.activity.settingsactivityviews.DatabaseManagementView
import io.github.mbp16.dishwidget.activity.settingsactivityviews.GetMealSettingView
import io.github.mbp16.dishwidget.activity.settingsactivityviews.MainScreenSettingView
import io.github.mbp16.dishwidget.ui.theme.DishWidgetTheme

class SettingsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DishWidgetTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SettingView(this)
                }
            }
        }
    }
}

@Composable
fun SettingView(activity: Activity) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MainScreenSettingView(activity)
        DatabaseManagementView(activity)
        GetMealSettingView(activity)
        AppInfoView(activity)
    }
}