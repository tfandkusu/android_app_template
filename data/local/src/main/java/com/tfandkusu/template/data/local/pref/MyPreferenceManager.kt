package com.tfandkusu.template.data.local.pref

import android.annotation.SuppressLint
import android.content.SharedPreferences
import javax.inject.Inject

interface MyPreferenceManager {
    fun countUpStartupTimes()

    fun getStartupTimes(): Int

    fun clear()
}

class MyPreferenceManagerImpl @Inject constructor(
    private val pref: SharedPreferences
) : MyPreferenceManager {

    companion object {
        private const val PREF_STARTUP_TIMES = "startupTimes"
    }

    override fun countUpStartupTimes() {
        val startupTimes = pref.getInt(PREF_STARTUP_TIMES, 0)
        val editor = pref.edit()
        editor.putInt(PREF_STARTUP_TIMES, startupTimes + 1)
        editor.apply()
    }

    override fun getStartupTimes(): Int {
        return pref.getInt(PREF_STARTUP_TIMES, 0)
    }

    @SuppressLint("ApplySharedPref")
    override fun clear() {
        pref.edit().clear().commit()
    }
}
