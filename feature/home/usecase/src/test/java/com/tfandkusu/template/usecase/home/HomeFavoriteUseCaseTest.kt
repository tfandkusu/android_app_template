package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.util.MyTestRule
import io.kotest.common.runBlocking
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeFavoriteUseCaseTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var repository: GithubRepoRepository

    private lateinit var useCase: HomeFavoriteUseCase

    @Before
    fun setUp() {
        useCase = HomeFavoriteUseCaseImpl(repository)
    }

    @Test
    fun execute() = runBlocking {
        useCase.execute(1L, true)
        coVerifySequence {
            repository.favorite(1L, true)
        }
    }
}
