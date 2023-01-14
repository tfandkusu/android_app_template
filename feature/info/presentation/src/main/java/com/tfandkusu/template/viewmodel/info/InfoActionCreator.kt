package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.usecase.info.InfoOnClickAboutUseCase
import com.tfandkusu.template.viewmodel.ActionCreator
import com.tfandkusu.template.viewmodel.Dispatcher
import javax.inject.Inject

interface InfoActionCreator : ActionCreator<InfoEvent, InfoAction>

class InfoActionCreatorImpl @Inject constructor(
    private val onClickAboutUseCase: InfoOnClickAboutUseCase
) : InfoActionCreator {
    override suspend fun event(event: InfoEvent, dispatcher: Dispatcher<InfoAction>) {
        when (event) {
            InfoEvent.OnClickOssLicense -> {
                dispatcher.dispatch(InfoAction.CallOssLicensesActivity)
            }
            InfoEvent.OnClickAbout -> {
                val result = onClickAboutUseCase.execute()
                dispatcher.dispatch(InfoAction.ShowAbout(result.numberOfStarts))
            }
            InfoEvent.CloseAbout -> {
                dispatcher.dispatch(InfoAction.CloseAbout)
            }
        }
    }
}
