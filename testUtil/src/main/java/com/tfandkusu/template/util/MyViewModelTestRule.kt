package com.tfandkusu.template.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MyViewModelTestRule(private val testSubject: Any) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return RuleChain.outerRule(InstantTaskExecutorRule())
            .around(MyCoroutineTestRule())
            .around(MyTestRule(testSubject))
            .apply(base, description)
    }
}
