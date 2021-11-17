package com.tfandkusu.template.data.repository

import com.tfandkusu.template.data.local.CreatedLocalDataStore
import com.tfandkusu.template.data.local.GithubRepoLocalDataStore
import com.tfandkusu.template.data.local.entity.LocalCreated
import com.tfandkusu.template.data.remote.GithubRemoteDataStore
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.util.CurrentTimeGetter
import com.tfandkusu.template.util.parseUTC
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GithubRepoRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var remoteDataStore: GithubRemoteDataStore

    @MockK(relaxed = true)
    private lateinit var localDataStore: GithubRepoLocalDataStore

    @MockK(relaxed = true)
    private lateinit var createdLocalDataStore: CreatedLocalDataStore

    @MockK(relaxed = true)
    private lateinit var currentTimeGetter: CurrentTimeGetter

    private lateinit var repository: GithubRepoRepository

    /**
     * Sample data
     */
    private lateinit var repos: List<GithubRepo>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = GithubRepoRepositoryImpl(
            remoteDataStore,
            localDataStore,
            createdLocalDataStore,
            currentTimeGetter
        )
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
        this.repos = listOf(repo1, repo2, repo3)
    }

    @Test
    fun isCacheExpiredTrue() = runBlocking {
        coEvery {
            createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO)
        } returns 1 * 60 * 60 * 1000L
        every {
            currentTimeGetter.get()
        } returns 2 * 60 * 60 * 1000L + 1
        repository.isCacheExpired() shouldBe true
        coVerifySequence {
            createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO)
            currentTimeGetter.get()
        }
    }

    @Test
    fun isCacheExpiredFalse() = runBlocking {
        coEvery {
            createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO)
        } returns 1 * 60 * 60 * 1000L
        every {
            currentTimeGetter.get()
        } returns 2 * 60 * 60 * 1000L
        repository.isCacheExpired() shouldBe false
        coVerifySequence {
            createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO)
            currentTimeGetter.get()
        }
    }

    @Test
    fun fetch() = runBlocking {
        coEvery {
            remoteDataStore.listRepositories()
        } returns repos
        repository.fetch()
        coVerifySequence {
            remoteDataStore.listRepositories()
            localDataStore.save(repos)
        }
    }

    @Test
    fun listAsFlow() = runBlocking {
        every {
            localDataStore.listAsFlow()
        } returns flow {
            emit(repos)
        }
        repository.listAsFlow().first() shouldBe repos
        coVerifySequence {
            localDataStore.listAsFlow()
        }
    }
}
