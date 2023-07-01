package com.tfandkusu.template.viewmodel.home

import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.usecase.home.HomeFavoriteUseCase
import com.tfandkusu.template.usecase.home.HomeLoadUseCase
import com.tfandkusu.template.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.template.util.MyTestRule
import com.tfandkusu.template.viewmodel.Dispatcher
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActionCreatorTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var dispatcher: Dispatcher<HomeAction>

    @MockK
    private lateinit var loadUseCase: HomeLoadUseCase

    @MockK
    private lateinit var onCreateUseCase: HomeOnCreateUseCase

    @MockK
    private lateinit var favoriteUseCase: HomeFavoriteUseCase

    private lateinit var actionCreator: HomeActionCreator

    @Before
    fun setUp() {
        actionCreator = HomeActionCreatorImpl(loadUseCase, onCreateUseCase, favoriteUseCase)
    }

    @Test
    fun onCreate() = runBlocking {
        val repos = GitHubRepoCatalog.getList()
        every {
            onCreateUseCase.execute()
        } returns flow {
            emit(repos)
        }
        actionCreator.event(HomeEvent.OnCreate, dispatcher)
        coVerifySequence {
            onCreateUseCase.execute()
            dispatcher.dispatch(HomeAction.UpdateGitHubRepoList(repos))
        }
    }

    @Test
    fun loadSuccess() = runBlocking {
        actionCreator.event(HomeEvent.Load, dispatcher)
        coVerifySequence {
            dispatcher.dispatch(HomeAction.StartLoad)
            loadUseCase.execute()
            dispatcher.dispatch(HomeAction.SuccessLoad)
        }
    }

//    @Test
//    fun loadError() = runBlocking {
//        coEvery {
//            loadUseCase.execute()
//        } throws NetworkErrorException
//        actionCreator.event(HomeEvent.Load, dispatcher)
//        coVerifySequence {
//            dispatcher.dispatch(HomeAction.StartLoad)
//            loadUseCase.execute()
//            dispatcher.dispatch(HomeAction.ErrorLoad(NetworkErrorException))
//        }
//    }

    @Test
    fun favorite() = runBlocking {
        actionCreator.event(HomeEvent.Favorite(2L, true), dispatcher)
        actionCreator.event(HomeEvent.Favorite(2L, false), dispatcher)
        coVerifySequence {
            favoriteUseCase.execute(2L, true)
            favoriteUseCase.execute(2L, false)
        }
    }
}
