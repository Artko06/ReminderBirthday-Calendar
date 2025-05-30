// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false

    // Dagger - Hilt
    alias(libs.plugins.dagger.hilt.android) apply false

    // Google services
    alias(libs.plugins.gms.google.services) apply false
}