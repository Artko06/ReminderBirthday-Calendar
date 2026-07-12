package com.artkotlindev.reminderbirthday_calendar

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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

        setContent {
            val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
            val theme = mainActivityViewModel.themeType.collectAsState().value
            val language = mainActivityViewModel.languageType.collectAsState().value

            val isDarkTheme = when (theme) {
                ThemeType.DARK -> true
                ThemeType.LIGHT -> false
                ThemeType.SYSTEM -> isSystemInDarkTheme()
            }

            LaunchedEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkTheme }
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkTheme }
                    )
                )
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
                    NavigationScreen()
                }
            }
        }
    }
}

fun updateLocale(context: Context, locale: Locale): Context {
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    return context.createConfigurationContext(config)
}

