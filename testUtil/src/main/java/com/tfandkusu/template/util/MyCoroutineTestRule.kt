package com.tfandkusu.template.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MyCoroutineTestRule : TestWatcher(), TestRule {

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
