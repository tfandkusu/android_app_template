package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.error.NetworkErrorException
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.template.usecase.home.HomeLoadUseCase
import com.tfandkusu.template.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.template.util.MyViewModelTestRule
import com.tfandkusu.template.viewmodel.error.ApiErrorState
import com.tfandkusu.template.viewmodel.mockStateObserver
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val rule = MyViewModelTestRule(this)

    @MockK
    private lateinit var loadUseCase: HomeLoadUseCase

    @MockK
    private lateinit var onCreateUseCase: HomeOnCreateUseCase

    @MockK
    private lateinit var favoriteUseCase: HomeFavoriteUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModelImpl(loadUseCase, onCreateUseCase, favoriteUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onCreate() = runTest {
        val repos = GitHubRepoCatalog.getList()
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.OnCreate)
        verifySequence {
            mockStateObserver.onChanged(HomeState())
            onCreateUseCase.execute()
            mockStateObserver.onChanged(
                HomeState(
                    items = repos.map {
                        HomeStateItem(it)
                    }
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadSuccess() = runTest {
        val stateMockObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.Load)
        coVerifySequence {
            stateMockObserver.onChanged(HomeState())
            stateMockObserver.onChanged(HomeState(progress = true))
            loadUseCase.execute()
            stateMockObserver.onChanged(HomeState(progress = false))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadError() = runTest {
        coEvery {
            loadUseCase.execute()
        } throws NetworkErrorException
        val stateMockObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.Load)
        coVerifySequence {
            stateMockObserver.onChanged(HomeState())
            stateMockObserver.onChanged(HomeState(progress = true))
            loadUseCase.execute()
            stateMockObserver.onChanged(
                HomeState(
                    progress = false,
                    error = ApiErrorState(network = true)
                )
            )
        }
    }

    @Test
    fun favorite() {
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
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        val items = listOf(
            HomeStateItem(repo1),
            HomeStateItem(repo2),
            HomeStateItem(repo3)
        )
        val mockStateObserver = viewModel.state.mockStateObserver()
        viewModel.event(HomeEvent.OnCreate)
        viewModel.event(HomeEvent.Favorite(2L, true))
        viewModel.event(HomeEvent.Favorite(2L, false))
        coVerifySequence {
            mockStateObserver.onChanged(HomeState())
            onCreateUseCase.execute()
            mockStateObserver.onChanged(HomeState(items = items))
            favoriteUseCase.execute(2L, true)
            favoriteUseCase.execute(2L, false)
        }
    }
}
