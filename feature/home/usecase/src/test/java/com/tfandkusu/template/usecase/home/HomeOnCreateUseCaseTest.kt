package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.data.repository.StartupTimesRepository
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomeOnCreateUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var startupTimesRepository: StartupTimesRepository

    @MockK(relaxed = true)
    private lateinit var githubRepoRepository: GithubRepoRepository

    private lateinit var useCase: HomeOnCreateUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = HomeOnCreateUseCaseImpl(githubRepoRepository, startupTimesRepository)
    }

    @Test
    fun execute() = runBlocking {
        val repos = GitHubRepoCatalog.getList()
        every {
            githubRepoRepository.listAsFlow()
        } returns flow {
            emit(repos)
        }
        useCase.execute().first() shouldBe repos
        verifySequence {
            startupTimesRepository.countUp()
            githubRepoRepository.listAsFlow()
        }
    }
}
