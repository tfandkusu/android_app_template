package com.tfandkusu.template.util

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Reference: https://github.com/mockk/mockk/blob/master/modules/mockk/src/jvmMain/kotlin/io/mockk/junit4/MockKRule.kt
 */
class MyTestRule(private val testSubject: Any) : TestWatcher(), TestRule {
    override fun starting(description: Description) {
        super.starting(description)
        // Add relaxUnitFun
        MockKAnnotations.init(testSubject, relaxUnitFun = true)
    }

    override fun finished(description: Description) {
        super.finished(description)
        unmockkAll()
    }
}
