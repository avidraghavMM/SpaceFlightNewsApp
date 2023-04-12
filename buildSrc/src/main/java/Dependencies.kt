/** This file contains versions of all the dependencies used in the module  */

object Lib { // ktlint-disable filename
    object AndroidX {

        private const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        private const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"

        val list = listOf(appCompat, legacySupport)

        object LifecycleScopes {
            private const val LIFECYCLE_SCOPE_VERSION = "2.3.1"
            private const val viewModelScope =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_SCOPE_VERSION"
            private const val otherScopes =
                "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_SCOPE_VERSION"

            val list = listOf(viewModelScope, otherScopes)
        }

        object Room {
            private const val ROOM_VERSION = "2.4.0"
            private const val room = "androidx.room:room-runtime:$ROOM_VERSION"
            const val roomCompiler = "androidx.room:room-compiler:$ROOM_VERSION"
            private const val roomPaging = "androidx.room:room-paging:$ROOM_VERSION"
            private const val roomKtx = "androidx.room:room-ktx:$ROOM_VERSION"

            val list = listOf(room, roomPaging, roomKtx)
        }

        object Paging {
            private const val PAGING_VERSION = "3.1.1"
            const val paging3 = "androidx.paging:paging-runtime-ktx:$PAGING_VERSION"
            const val pagingCompose = "androidx.paging:paging-compose:1.0.0-alpha17"

            val list = listOf(paging3, pagingCompose)
        }

        object Compose {
            private const val COMPOSE_BOM_VERSION = "2022.10.00"
            const val composeBom = "androidx.compose:compose-bom:$COMPOSE_BOM_VERSION"

            private const val composeRuntime = "androidx.compose.runtime:runtime"
            private const val composeUI = "androidx.compose.ui:ui"
            private const val composeFoundation = "androidx.compose.foundation:foundation"
            private const val composeLayout = "androidx.compose.foundation:foundation-layout"
            private const val composeMaterial = "androidx.compose.material:material"
            private const val composeLiveData = "androidx.compose.runtime:runtime-livedata"
            private const val composeTooling = "androidx.compose.ui:ui-tooling"
            private const val composePreview = "androidx.compose.ui:ui-tooling-preview"
            private const val accompanistThemeAdapter =
                "com.google.accompanist:accompanist-themeadapter-material:0.28.0"
            private const val composeNavigation = "androidx.navigation:navigation-compose:2.5.3"

            val list = listOf(
                composeRuntime,
                composeUI,
                composeFoundation,
                composeLayout,
                composeMaterial,
                composeLiveData,
                composeTooling,
                composePreview,
                accompanistThemeAdapter,
                composeNavigation
            )
        }

        object CustomTab {
            private const val CUSTOM_TAB_VERSION = "1.4.0"
            const val customTab = "androidx.browser:browser:$CUSTOM_TAB_VERSION"
        }

        object AndroidTesting {
            const val androidXJunitExtenstion = "androidx.test.ext:junit:1.1.5"
        }
    }

    object Kotlin {
        private const val KOTLIN_VERSION = "1.7.20"
        private const val KTX_CORE_VERSION = "1.9.0"
        private const val COROUTINES_VERSION = "1.6.4"
        private const val kotlinStandardLib =
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"
        private const val kotlinCoreLib = "androidx.core:core-ktx:$KTX_CORE_VERSION"
        private const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
        private const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"

        val list = listOf(kotlinStandardLib, kotlinCoreLib, coroutinesCore, coroutinesAndroid)
    }

    object Google {
        object DI {
            private const val DAGGER_HILT_VERSION = "2.42"
            private const val hilt = "com.google.dagger:hilt-android:2.42"
            const val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.42"
            private const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"

            val list = listOf(hilt, hiltNavigationCompose)
        }

        object MaterialDesign {
            const val materialDesign = "com.google.android.material:material:1.4.0"
        }
    }

    object ImageLoading {
        private const val COIL_VERSION = "2.2.2"
        const val coil = "io.coil-kt:coil-compose:$COIL_VERSION"
    }

    object Networking {
        private const val RETROFIT_VERSION = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
        const val gson = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
        const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"

        val list = listOf(retrofit, gson, okhttpInterceptor)
    }

    object UnitTesting {
        const val junit = "junit:junit:4.+"
    }
}
