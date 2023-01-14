package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.model.GithubRepo

sealed class HomeAction {
    object StartLoad : HomeAction()

    object SuccessLoad : HomeAction()

    data class ErrorLoad(val e: Throwable) : HomeAction()

    data class UpdateGitHubRepoList(val repos: List<GithubRepo>) : HomeAction()
}
