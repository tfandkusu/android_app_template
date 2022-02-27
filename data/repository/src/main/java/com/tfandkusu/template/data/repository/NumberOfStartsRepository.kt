package com.tfandkusu.template.data.repository

import com.tfandkusu.template.data.local.pref.MyPreferenceManager
import javax.inject.Inject

interface NumberOfStartsRepository {
    fun countUp()

    fun get(): Int
}

class NumberOfStartsRepositoryImpl @Inject constructor(
    private val pref: MyPreferenceManager
) : NumberOfStartsRepository {
    override fun countUp() {
        pref.countUpNumberOfStarts()
    }

    override fun get(): Int {
        return pref.getNumberOfStarts()
    }
}
