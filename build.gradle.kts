// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
}
buildscript {
    val kotlin_version = "1.7.20"
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.1.0")
        // hilt-android-gradle-plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

// this declaration is not needed as clean task is now registered automatically with gradle
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
