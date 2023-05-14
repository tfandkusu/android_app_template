package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.viewmodel.ReduxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    actionCreator: HomeActionCreator,
    reducer: HomeReducer
) : ReduxViewModel<HomeEvent, HomeAction, HomeState, HomeEffect>(
    actionCreator,
    reducer
),
    HomeViewModel {
    override fun createDefaultState(): HomeState {
        return HomeState()
    }
}
