package com.tfandkusu.template.viewmodel.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCaseResult
import com.tfandkusu.template.viewmodel.mockStateObserver
import io.mockk.MockKAnnotations
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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
    private val testDispatcher = UnconfinedTestDispatcher()

    @MockK(relaxed = true)
    private lateinit var onClickAboutUseCase: InfoOnClickAboutUseCase

    private lateinit var viewModel: InfoViewModelImpl

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = InfoViewModelImpl(onClickAboutUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun onClickAbout() = runTest {
        every {
            onClickAboutUseCase.execute()
        } returns InfoOnClickAboutUseCaseResult(3)
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(InfoEvent.OnClickAbout)
        viewModel.event(InfoEvent.CloseAbout)
        coVerifySequence {
            mockStateObserver.onChanged(InfoState())
            onClickAboutUseCase.execute()
            mockStateObserver.onChanged(InfoState(numberOfStarts = 3))
            mockStateObserver.onChanged(InfoState())
        }
    }
}
