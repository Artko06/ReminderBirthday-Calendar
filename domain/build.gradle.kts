plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies{
    implementation(libs.androidx.annotation.jvm)

    implementation(libs.kotlin.stdlib.jdk8) // Standard library of Kotlin
    implementation(libs.kotlinx.coroutines.core) // Kotlin coroutines
}
