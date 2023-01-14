package com.tfandkusu.template.usecase.info

import com.tfandkusu.template.data.repository.NumberOfStartsRepository
import com.tfandkusu.template.util.MyTestRule
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InfoOnClickAboutUseCaseTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var repository: NumberOfStartsRepository

    private lateinit var useCase: InfoOnClickAboutUseCase

    @Before
    fun setUp() {
        useCase = InfoOnClickAboutUseCaseImpl(repository)
    }

    @Test
    fun execute() {
        every {
            repository.get()
        } returns 3
        useCase.execute() shouldBe InfoOnClickAboutUseCaseResult(3)
        verifySequence {
            repository.get()
        }
    }
}
