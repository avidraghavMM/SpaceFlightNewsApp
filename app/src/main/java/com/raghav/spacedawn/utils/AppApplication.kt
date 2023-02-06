package com.raghav.spacedawn.utils

import android.app.Application
import com.raghav.spacedawn.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {

    companion object {
        lateinit var INSTANCE: AppApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    fun getLaunchLibraryBaseUrl(): String {
        if (BuildConfig.DEBUG) {
            return Constants.DEBUG_BASE_URL_LAUNCHLIBRARY
        }
        return Constants.BASE_URL_LAUNCHLIBRARY
    }
}
