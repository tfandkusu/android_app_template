package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.data.repository.DummyRepository
import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.util.MyTestRule
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeLoadUseCaseTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var repository: GithubRepoRepository

    @MockK
    private lateinit var dummyRepository: DummyRepository

    private lateinit var useCase: HomeLoadUseCase

    @Before
    fun setUp() {
        useCase = HomeLoadUseCaseImpl(repository, dummyRepository)
    }

    @Test
    fun fetch() = runBlocking {
        coEvery {
            repository.isCacheExpired()
        } returns true
        every {
            dummyRepository.getFlag()
        } returns true
        useCase.execute()
        coVerifySequence {
            repository.isCacheExpired()
            dummyRepository.getFlag()
            repository.fetch()
        }
    }

    @Test
    fun useCache() = runBlocking {
        coEvery {
            repository.isCacheExpired()
        } returns false
        useCase.execute()
        coVerifySequence {
            repository.isCacheExpired()
        }
    }
}
