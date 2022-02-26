package com.tfandkusu.template.data.repository

import com.tfandkusu.template.data.local.pref.MyPreferenceManager
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test

class StartupTimesRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var pref: MyPreferenceManager

    private lateinit var repository: StartupTimesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = StartupTimesRepositoryImpl(pref)
    }

    @Test
    fun countUp() {
        repository.countUp()
        verifySequence {
            pref.countUpStartupTimes()
        }
    }

    @Test
    fun get() {
        every {
            pref.getStartupTimes()
        } returns 3
        repository.get() shouldBe 3
        verifySequence {
            pref.getStartupTimes()
        }
    }
}
