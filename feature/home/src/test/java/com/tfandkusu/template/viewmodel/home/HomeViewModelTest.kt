package com.tfandkusu.template.viewmodel.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.usecase.home.HomeLoadUseCase
import com.tfandkusu.template.usecase.home.HomeOnCreateUseCase
import com.tfandkusu.template.util.parseUTC
import com.tfandkusu.template.viewmodel.mockStateObserver
import io.mockk.MockKAnnotations
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK(relaxed = true)
    private lateinit var loadUseCase: HomeLoadUseCase

    @MockK(relaxed = true)
    private lateinit var onCreateUseCase: HomeOnCreateUseCase

    private lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = HomeViewModelImpl(loadUseCase, onCreateUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onCreate() = testDispatcher.runBlockingTest {
        val repo1 = GithubRepo(
            229475311L,
            "observe_room",
            listOf(
                "Check how to use Room to observe SQLite database ",
                "and reflect the changes in the RecyclerView."
            ).joinToString(separator = ""),
            parseUTC("2021-10-29T00:15:46Z"),
            "Kotlin",
            "https://github.com/tfandkusu/observe_room",
            false
        )
        val repo2 = GithubRepo(
            320900929L,
            "groupie_sticky_header_sample",
            "Sample app for sticky header on the groupie",
            parseUTC("2021-01-19T19:46:27Z"),
            "Java",
            "https://github.com/tfandkusu/groupie_sticky_header_sample",
            false
        )
        val repo3 = GithubRepo(
            343133709L,
            "conference-app-2021",
            "The Official App for DroidKaigi 2021",
            parseUTC("2021-09-21T16:56:04Z"),
            "Kotlin",
            "https://github.com/tfandkusu/conference-app-2021",
            true
        )
        val repos = listOf(repo1, repo2, repo3)
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
            mockStateObserver.onChanged(HomeState(progress = false, repos = repos))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadSuccess() = testDispatcher.runBlockingTest {
        viewModel.event(HomeEvent.Load)
        coVerifySequence {
            loadUseCase.execute()
        }
    }
}
