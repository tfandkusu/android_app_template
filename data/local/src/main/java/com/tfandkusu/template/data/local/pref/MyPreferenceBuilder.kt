package com.tfandkusu.template.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object MyPreferenceBuilder {
    fun build(applicationContext: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }
}
