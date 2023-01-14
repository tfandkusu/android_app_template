package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCaseResult
import com.tfandkusu.template.util.MyTestRule
import com.tfandkusu.template.viewmodel.Dispatcher
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InfoActionCreatorTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var onClickAboutUseCase: InfoOnClickAboutUseCase

    @MockK
    private lateinit var dispatcher: Dispatcher<InfoAction>

    private lateinit var actionCreator: InfoActionCreator

    @Before
    fun setUp() {
        actionCreator = InfoActionCreatorImpl(onClickAboutUseCase)
    }

    @Test
    fun onClickOssLicense() = runBlocking {
        actionCreator.event(InfoEvent.OnClickOssLicense, dispatcher)
        coVerifySequence {
            dispatcher.dispatch(InfoAction.CallOssLicensesActivity)
        }
    }

    @Test
    fun onClickAbout() = runBlocking {
        coEvery {
            onClickAboutUseCase.execute()
        } returns InfoOnClickAboutUseCaseResult(3)
        actionCreator.event(InfoEvent.OnClickAbout, dispatcher)
        coVerifySequence {
            onClickAboutUseCase.execute()
            dispatcher.dispatch(InfoAction.ShowAbout(3))
        }
    }

    @Test
    fun closeAbout() = runBlocking {
        actionCreator.event(InfoEvent.CloseAbout, dispatcher)
        coVerifySequence {
            dispatcher.dispatch(InfoAction.CloseAbout)
        }
    }
}
