package com.tfandkusu.template.data.repository

import com.tfandkusu.template.data.local.pref.MyPreferenceManager
import javax.inject.Inject

interface StartupTimesRepository {
    fun countUp()

    fun get(): Int
}

class StartupTimesRepositoryImpl @Inject constructor(
    private val pref: MyPreferenceManager
) : StartupTimesRepository {
    override fun countUp() {
        pref.countUpStartupTimes()
    }

    override fun get(): Int {
        return pref.getStartupTimes()
    }
}
