package com.artkotlindev.reminderbirthday_calendar

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.artkotlindev.domain.models.mappers.toLocale
import com.artkotlindev.domain.models.settings.ThemeType
import com.artkotlindev.reminderbirthday_calendar.presentation.navigation.NavigationScreen
import com.artkotlindev.reminderbirthday_calendar.presentation.viewModel.MainActivityViewModel
import com.artkotlindev.reminderbirthday_calendar.ui.theme.ReminderBirthday_CalendarTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

val LocalTheme = compositionLocalOf { ThemeType.DARK }
val LocalizedContext = compositionLocalOf<Context> { error("LocalizedContext not provided") }


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
            val theme = mainActivityViewModel.themeType.collectAsState().value
            val language = mainActivityViewModel.languageType.collectAsState().value
            
            val isDarkTheme = when (theme) {
                ThemeType.DARK -> true
                ThemeType.LIGHT -> false
                ThemeType.SYSTEM -> isSystemInDarkTheme()
            }

            val localizedContext = updateLocale(
                context = this,
                locale = language.toLocale()
            )

            CompositionLocalProvider(
                LocalTheme provides if (isDarkTheme) ThemeType.DARK else ThemeType.LIGHT,
                LocalizedContext provides localizedContext
                ) {
                ReminderBirthday_CalendarTheme(
                    darkTheme = isDarkTheme
                ) {
                    val navigationBarColor = NavigationBarDefaults.containerColor

                    val view = LocalView.current
                    val window = (view.context as Activity).window

                    SideEffect {
                        updateSystemColors(
                            window = window,
                            isDarkTheme = isDarkTheme,
                            navigationBarColor = navigationBarColor
                        )
                    }

                    NavigationScreen()
                }
            }
        }
    }
}

fun updateSystemColors(
    window: Window,
    isDarkTheme: Boolean,
    navigationBarColor: Color
) {
    window.statusBarColor = Color.Transparent.toArgb()
    window.navigationBarColor = navigationBarColor.toArgb()

    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = !isDarkTheme
        isAppearanceLightNavigationBars = !isDarkTheme
    }
}

fun updateLocale(context: Context, locale: Locale): Context {
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    return context.createConfigurationContext(config)
}

