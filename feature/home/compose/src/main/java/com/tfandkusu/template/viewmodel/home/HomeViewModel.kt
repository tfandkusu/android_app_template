package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.viewmodel.UnidirectionalViewModel
import com.tfandkusu.template.viewmodel.error.ApiErrorViewModelHelper

sealed class HomeEvent {

    object OnCreate : HomeEvent()

    object Load : HomeEvent()

    data class ItemClick(val id: Long) : HomeEvent()
}

sealed class HomeEffect

data class HomeStateItem(
    val repo: GithubRepo,
    val selected: Boolean
)

data class HomeState(
    val progress: Boolean = true,
    val items: List<HomeStateItem> = listOf()
)

interface HomeViewModel : UnidirectionalViewModel<HomeEvent, HomeEffect, HomeState> {
    val error: ApiErrorViewModelHelper
}
