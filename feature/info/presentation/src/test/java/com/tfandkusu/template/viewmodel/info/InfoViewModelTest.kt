package com.tfandkusu.template.viewmodel.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCaseResult
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class InfoViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK(relaxed = true)
    private lateinit var onClickAboutUseCase: InfoOnClickAboutUseCase

    private lateinit var viewModel: InfoViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = InfoViewModel(onClickAboutUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun onClickAbout() = testDispatcher.runBlockingTest {
        every {
            onClickAboutUseCase.execute()
        } returns InfoOnClickAboutUseCaseResult(3)
        viewModel.event(InfoEvent.OnClickAbout)
        viewModel.effect.first() shouldBe InfoEffect.ShowAbout(3)
    }
}
