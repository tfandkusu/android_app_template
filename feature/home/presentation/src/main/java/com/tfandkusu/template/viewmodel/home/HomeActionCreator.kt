package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.template.usecase.home.HomeLoadUseCase
import com.tfandkusu.template.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.template.viewmodel.ActionCreator
import com.tfandkusu.template.viewmodel.Dispatcher
import javax.inject.Inject

interface HomeActionCreator : ActionCreator<HomeEvent, HomeAction>

class HomeActionCreatorImpl @Inject constructor(
    private val loadUseCase: HomeLoadUseCase,
    private val onCreateUseCase: HomeOnCreateUseCase,
    private val favoriteUseCase: HomeFavoriteUseCase
) : HomeActionCreator {
    override suspend fun event(event: HomeEvent, dispatcher: Dispatcher<HomeAction>) {
        when (event) {
            HomeEvent.Load -> {
                dispatcher.dispatch(HomeAction.StartLoad)
                try {
                    loadUseCase.execute()
                    dispatcher.dispatch(HomeAction.SuccessLoad)
                } catch (e: Throwable) {
                    dispatcher.dispatch(HomeAction.ErrorLoad(e))
                }
            }
            HomeEvent.OnCreate -> {
                onCreateUseCase.execute().collect { repos ->
                    dispatcher.dispatch(HomeAction.UpdateGitHubRepoList(repos))
                }
            }
            is HomeEvent.Favorite -> {
                favoriteUseCase.execute(event.id, event.on)
            }
        }
    }
}
