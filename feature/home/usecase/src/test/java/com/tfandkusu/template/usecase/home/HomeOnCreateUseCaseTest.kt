package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.data.repository.NumberOfStartsRepository
import com.tfandkusu.template.util.MyTestRule
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeOnCreateUseCaseTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var numberOfStartsRepository: NumberOfStartsRepository

    @MockK
    private lateinit var githubRepoRepository: GithubRepoRepository

    private lateinit var useCase: HomeOnCreateUseCase

    @Before
    fun setUp() {
        useCase = HomeOnCreateUseCaseImpl(githubRepoRepository, numberOfStartsRepository)
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
            numberOfStartsRepository.countUp()
            githubRepoRepository.listAsFlow()
        }
    }
}
