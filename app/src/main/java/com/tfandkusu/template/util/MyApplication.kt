package com.tfandkusu.template.util

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.tfandkusu.template.BuildConfig
import com.tfandkusu.template.model.AppInfo
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppInfo.versionName = BuildConfig.VERSION_NAME
        // To show oss license by using oss-licenses-plugin
        DynamicColors.applyToActivitiesIfAvailable(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
