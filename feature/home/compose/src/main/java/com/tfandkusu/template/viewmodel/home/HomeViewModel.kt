package com.tfandkusu.template.viewmodel.home

import androidx.compose.runtime.Stable
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.viewmodel.UnidirectionalViewModel
import com.tfandkusu.template.viewmodel.error.ApiErrorState

sealed class HomeEvent {

    object OnCreate : HomeEvent()

    object Load : HomeEvent()

    data class Favorite(val id: Long, val on: Boolean) : HomeEvent()
}

sealed class HomeEffect

@Stable
data class HomeStateItem(
    val repo: GithubRepo
)

data class LanguageRepositoryCount(
    val language: String,
    val count: Int
)

data class HomeState(
    val progress: Boolean = true,
    val items: List<HomeStateItem> = listOf(),
    val error: ApiErrorState = ApiErrorState()
) {
    val languageRepositoryCountList: List<LanguageRepositoryCount>
        get() {
            val countMap = mutableMapOf<String, Int>()
            for (item in items) {
                val language = item.repo.language
                val count = countMap[language] ?: 0
                countMap[language] = count + 1
            }
            return countMap.map { entry ->
                LanguageRepositoryCount(entry.key, entry.value)
            }.sortedByDescending { it.count }
        }
}

interface HomeViewModel : UnidirectionalViewModel<HomeEvent, HomeEffect, HomeState>
