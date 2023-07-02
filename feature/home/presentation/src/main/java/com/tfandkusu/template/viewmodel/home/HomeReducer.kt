package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.viewmodel.Reducer
import com.tfandkusu.template.viewmodel.StateEffect
import com.tfandkusu.template.viewmodel.error.ApiErrorState
import com.tfandkusu.template.viewmodel.error.mapToApiErrorState
import javax.inject.Inject

interface HomeReducer : Reducer<HomeAction, HomeState, HomeEffect>

class HomeReducerImpl @Inject constructor() : HomeReducer {
    override fun reduce(state: HomeState, action: HomeAction): StateEffect<HomeState, HomeEffect> {
        when (action) {
            HomeAction.StartLoad -> {
                return StateEffect(state.copy(error = ApiErrorState(), progress = true))
            }

            HomeAction.SuccessLoad -> {
                return StateEffect(state.copy(progress = false))
            }

            is HomeAction.ErrorLoad -> {
                return StateEffect(
                    state.copy(
                        error = mapToApiErrorState(action.e),
                        progress = false
                    )
                )
            }

            is HomeAction.UpdateGitHubRepoList -> {
                return StateEffect(
                    state.copy(
                        items = action.repos.map {
                            HomeStateItem(
                                it
                            )
                        }
                    )
                )
            }

            HomeAction.Dummy -> {
                return StateEffect(state)
            }
        }
    }
}
