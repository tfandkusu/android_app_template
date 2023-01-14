package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCaseResult
import com.tfandkusu.template.util.MyViewModelTestRule
import com.tfandkusu.template.viewmodel.mockStateObserver
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InfoViewModelTest {

    @get:Rule
    val rule = MyViewModelTestRule(this)

    @MockK
    private lateinit var onClickAboutUseCase: InfoOnClickAboutUseCase

    private lateinit var viewModel: InfoViewModelImpl

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = InfoViewModelImpl(onClickAboutUseCase)
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
