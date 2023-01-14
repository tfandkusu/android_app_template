package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.viewmodel.StateEffect
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class InfoReducerTest {

    private lateinit var reducer: InfoReducer

    @Before
    fun setUp() {
        reducer = InfoReducerImpl()
    }

    @Test
    fun callOssLicensesActivity() {
        reducer.reduce(
            InfoState(),
            InfoAction.CallOssLicensesActivity
        ) shouldBe StateEffect(InfoState(), InfoEffect.CallOssLicensesActivity)
    }

    @Test
    fun showAbout() {
        reducer.reduce(
            InfoState(),
            InfoAction.ShowAbout(3)
        ) shouldBe StateEffect(InfoState(numberOfStarts = 3))
    }

    @Test
    fun closeAbout() {
        reducer.reduce(
            InfoState(numberOfStarts = 3),
            InfoAction.CloseAbout
        ) shouldBe StateEffect(InfoState())
    }
}
