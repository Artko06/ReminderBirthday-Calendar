plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    kotlin("kapt")

    // Dagger - Hilt
    alias(libs.plugins.dagger.hilt.android)

    // Google services
    alias(libs.plugins.gms.google.services)
}

android {
    namespace = "com.example.reminderbirthday_calendar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.reminderbirthday_calendar"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    kotlinOptions {
        jvmTarget = "11"
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
    kapt(libs.androidx.room.compiler)

    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    kapt (libs.dagger.hilt.android.compiler)

    // Dagger - Hilt (navigation)
    implementation (libs.androidx.hilt.navigation.compose)
    kapt (libs.androidx.hilt.compiler)

    // Jetpack Compose permissions
    implementation(libs.accompanist.permissions)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)

    // Splash screen
    implementation(libs.androidx.core.splashscreen) // Not release yet

//    // Local unit tests
//    testImplementation("androidx.test:core:1.6.1")
//    testImplementation("junit:junit:4.13.2")
//    testImplementation("androidx.arch.core:core-testing:2.2.0")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
//    testImplementation("com.google.truth:truth:1.1.3")
//    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
//    testImplementation("io.mockk:mockk:1.10.5")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")
//
//    // Instrumentation tests
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.55")
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.55")
//    androidTestImplementation("junit:junit:4.13.2")
//    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
//    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
//    androidTestImplementation("com.google.truth:truth:1.1.3")
//    androidTestImplementation("androidx.test.ext:junit:1.2.1")
//    androidTestImplementation("androidx.test:core-ktx:1.6.1")
//    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
//    androidTestImplementation("io.mockk:mockk-android:1.10.5")
//    androidTestImplementation("androidx.test:runner:1.6.2")
}