package com.tfandkusu.template.data.repository

import com.tfandkusu.template.data.local.pref.MyPreferenceManager
import com.tfandkusu.template.util.MyTestRule
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NumberOfStartsRepositoryTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var pref: MyPreferenceManager

    private lateinit var repository: NumberOfStartsRepository

    @Before
    fun setUp() {
        repository = NumberOfStartsRepositoryImpl(pref)
    }

    @Test
    fun countUp() {
        repository.countUp()
        verifySequence {
            pref.countUpNumberOfStarts()
        }
    }

    @Test
    fun get() {
        every {
            pref.getNumberOfStarts()
        } returns 3
        repository.get() shouldBe 3
        verifySequence {
            pref.getNumberOfStarts()
        }
    }
}
