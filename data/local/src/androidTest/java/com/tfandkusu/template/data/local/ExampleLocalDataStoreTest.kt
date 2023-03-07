package com.tfandkusu.template.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tfandkusu.template.util.CurrentTimeGetter
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleLocalDataStoreTest {
    @MockK
    private lateinit var currentTimeGetter: CurrentTimeGetter

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test() {
        every {
            currentTimeGetter.get()
        } returns 3L
        1 + 1 shouldBe 2
        currentTimeGetter.get() shouldBe 3L
    }
}
