package com.tfandkusu.template.data.repository

import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.data.local.CreatedLocalDataStore
import com.tfandkusu.template.data.local.GithubRepoLocalDataStore
import com.tfandkusu.template.data.local.entity.LocalCreated
import com.tfandkusu.template.data.remote.GithubRemoteDataStore
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.util.CurrentTimeGetter
import com.tfandkusu.template.util.MyTestRule
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubRepoRepositoryTest {

    @get:Rule
    val rule = MyTestRule(this)

    @MockK
    private lateinit var remoteDataStore: GithubRemoteDataStore

    @MockK
    private lateinit var localDataStore: GithubRepoLocalDataStore

    @MockK
    private lateinit var createdLocalDataStore: CreatedLocalDataStore

    @MockK
    private lateinit var currentTimeGetter: CurrentTimeGetter

    private lateinit var repository: GithubRepoRepository

    /**
     * Sample data
     */
    private lateinit var repos: List<GithubRepo>

    @Before
    fun setUp() {
        repository = GithubRepoRepositoryImpl(
            remoteDataStore,
            localDataStore,
            createdLocalDataStore,
            currentTimeGetter
        )
        this.repos = GitHubRepoCatalog.getList()
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

    @Test
    fun favorite() = runBlocking {
        repository.favorite(1L, true)
        coVerifySequence {
            localDataStore.favorite(1L, true)
        }
    }
}
