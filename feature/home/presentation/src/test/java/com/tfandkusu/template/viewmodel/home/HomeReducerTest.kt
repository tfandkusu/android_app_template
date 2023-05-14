package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.error.NetworkErrorException
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.viewmodel.StateEffect
import com.tfandkusu.template.viewmodel.error.ApiErrorState
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class HomeReducerTest {

    private lateinit var reducer: HomeReducer

    @Before
    fun setUp() {
        reducer = HomeReducerImpl()
    }

    @Test
    fun startLoad() {
        reducer.reduce(
            HomeState(),
            HomeAction.StartLoad
        ) shouldBe StateEffect(
            HomeState(progress = true)
        )
    }

    @Test
    fun successLoad() {
        reducer.reduce(
            HomeState(progress = true),
            HomeAction.SuccessLoad
        ) shouldBe StateEffect(
            HomeState(progress = false)
        )
    }

    @Test
    fun errorLoad() {
        reducer.reduce(
            HomeState(progress = true),
            HomeAction.ErrorLoad(NetworkErrorException)
        ) shouldBe StateEffect(
            HomeState(progress = false, error = ApiErrorState(network = true))
        )
    }

    @Test
    fun updateGitHubRepoList() {
        val repo1 = mockk<GithubRepo> {
            every { id } returns 1L
        }
        val repo2 = mockk<GithubRepo> {
            every { id } returns 2L
        }
        val repo3 = mockk<GithubRepo> {
            every { id } returns 3L
        }
        val repos = listOf(repo1, repo2, repo3)
        val items = listOf(
            HomeStateItem(repo1),
            HomeStateItem(repo2),
            HomeStateItem(repo3)
        )
        reducer.reduce(
            HomeState(),
            HomeAction.UpdateGitHubRepoList(repos)
        ) shouldBe StateEffect(HomeState(items = items))
    }
}
