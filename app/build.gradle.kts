plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppVersions.COMPILE_SDK
    buildToolsVersion = AppVersions.BUILD_TOOL_VERSION

    defaultConfig {
        applicationId = AppVersions.APPLICATION_ID
        minSdk = AppVersions.MIN_SDK
        targetSdk = AppVersions.TARGET_SDK
        versionCode = AppVersions.versionCode
        versionName = AppVersions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {

    Lib.AndroidX.list.forEach(::implementation)
    Lib.AndroidX.LifecycleScopes.list.forEach(::implementation)
    Lib.Kotlin.list.forEach(::implementation)
    Lib.Networking.list.forEach(::implementation)
    Lib.AndroidX.Paging.list.forEach(::implementation)
    Lib.Google.DI.list.forEach(::implementation)

    implementation(Lib.Google.MaterialDesign.materialDesign)
    testImplementation(Lib.UnitTesting.junit)
    androidTestImplementation(Lib.AndroidX.AndroidTesting.androidXJunitExtenstion)

    Lib.AndroidX.Room.list.forEach(::implementation)
    add("kapt", Lib.AndroidX.Room.roomCompiler)

    // Compose
    val composeBom = platform(Lib.AndroidX.Compose.composeBom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    Lib.AndroidX.Compose.list.forEach(::implementation)

    // Coil for image loading
    implementation(Lib.ImageLoading.coil)

    // Custom Tab
    implementation(Lib.AndroidX.CustomTab.customTab)
}
kapt {
    correctErrorTypes = true
}
