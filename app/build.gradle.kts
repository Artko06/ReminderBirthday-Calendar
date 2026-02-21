plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.google.devtools.ksp)

    // Dagger - Hilt
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.artkotlindev.reminderbirthday_calendar"
    compileSdk = 36

    signingConfigs {
        create("release") {
            storeFile = file(project.property("RELEASE_STORE_FILE") as String)
            storePassword = project.property("RELEASE_STORE_PASSWORD") as String
            keyAlias = project.property("RELEASE_KEY_ALIAS") as String
            keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
        }
    }

    defaultConfig {
        applicationId = "com.artkotlindev.reminderbirthday_calendar"
        minSdk = 26
        targetSdk = 36
        versionCode = 1054
        versionName = "1.0.5.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        signingConfig = signingConfigs.getByName("release")
    }

    androidResources {
        localeFilters.addAll(listOf("en", "ru", "be"))
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    buildTypes {
        release {
            // Enables code-related app optimization.
            isMinifyEnabled = true

            // Enables resource shrinking.
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // icons extended
    implementation(libs.androidx.compose.material.icons.extended)

    // RoomDb
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)

    // Dagger - Hilt (navigation)
    implementation (libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)

    // Jetpack Compose permissions
    implementation(libs.accompanist.permissions)

    // For pick photos from gallery
    implementation(libs.coil.kt.compose)
}

