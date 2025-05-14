package com.example.reminderbirthday_calendar

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.settings.ThemeType
import com.example.reminderbirthday_calendar.presentation.navigation.NavigationScreen
import com.example.reminderbirthday_calendar.presentation.viewModel.MainActivityViewModel
import com.example.reminderbirthday_calendar.ui.theme.ReminderBirthday_CalendarTheme
import dagger.hilt.android.AndroidEntryPoint

val LocalTheme = compositionLocalOf { ThemeType.DARK }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
            val theme = mainActivityViewModel.themeType.collectAsState().value

            val isDarkTheme = when (theme) {
                ThemeType.DARK -> true
                ThemeType.LIGHT -> false
                ThemeType.SYSTEM -> isSystemInDarkTheme()
            }

            val view = LocalView.current
            val window = (view.context as Activity).window
            SideEffect {
                window.statusBarColor = Color.Transparent.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    !isDarkTheme
            }

            CompositionLocalProvider(LocalTheme provides if (isDarkTheme) ThemeType.DARK else ThemeType.LIGHT) {
                ReminderBirthday_CalendarTheme(
                    darkTheme = isDarkTheme
                ) {
                    NavigationScreen()
                }
            }
        }
    }
}
