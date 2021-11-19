package com.tfandkusu.template.usecase.home

import com.tfandkusu.template.data.repository.GithubRepoRepository
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.util.parseUTC
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
    private lateinit var repository: GithubRepoRepository

    private lateinit var useCase: HomeOnCreateUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = HomeOnCreateUseCaseImpl(repository)
    }

    @Test
    fun execute() = runBlocking {
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
            repository.listAsFlow()
        } returns flow {
            emit(repos)
        }
        useCase.execute().first() shouldBe repos
        verifySequence {
            repository.listAsFlow()
        }
    }
}
