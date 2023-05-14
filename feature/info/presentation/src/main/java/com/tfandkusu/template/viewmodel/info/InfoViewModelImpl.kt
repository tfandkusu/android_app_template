package com.tfandkusu.template.viewmodel.info

import com.tfandkusu.template.viewmodel.ReduxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl @Inject constructor(
    actionCreator: InfoActionCreator,
    reducer: InfoReducer
) : ReduxViewModel<InfoEvent, InfoAction, InfoState, InfoEffect>(actionCreator, reducer),
    InfoViewModel {
    override fun createDefaultState(): InfoState {
        return InfoState()
    }
}
