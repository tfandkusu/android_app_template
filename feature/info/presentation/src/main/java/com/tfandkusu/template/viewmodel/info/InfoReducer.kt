package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.viewmodel.Reducer
import com.tfandkusu.template.viewmodel.StateEffect
import javax.inject.Inject

interface InfoReducer : Reducer<InfoAction, InfoState, InfoEffect>

class InfoReducerImpl @Inject constructor() : InfoReducer {
    override fun reduce(state: InfoState, action: InfoAction): StateEffect<InfoState, InfoEffect> {
        return when (action) {
            is InfoAction.ShowAbout -> {
                StateEffect(state.copy(numberOfStarts = action.numberOfStarts))
            }
            InfoAction.CloseAbout -> {
                StateEffect(state.copy(numberOfStarts = null))
            }
            InfoAction.CallOssLicensesActivity -> {
                StateEffect(state, InfoEffect.CallOssLicensesActivity)
            }
        }
    }
}
