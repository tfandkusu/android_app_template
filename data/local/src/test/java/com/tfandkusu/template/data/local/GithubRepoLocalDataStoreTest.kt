package com.tfandkusu.template.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tfandkusu.template.data.local.db.AppDatabaseBuilder
import com.tfandkusu.template.data.local.entity.LocalCreated
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.util.CurrentTimeGetter
import com.tfandkusu.template.util.parseUTC
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GithubRepoLocalDataStoreTest {
    @MockK(relaxed = true)
    private lateinit var currentTimeGetter: CurrentTimeGetter

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val db = AppDatabaseBuilder.build(context, true)

    private lateinit var localDataStore: GithubRepoLocalDataStore

    private lateinit var createdLocalDataStore: CreatedLocalDataStore

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localDataStore = GithubRepoLocalDataStoreImpl(db, currentTimeGetter)
        createdLocalDataStore = CreatedLocalDataStoreImpl(db)
    }

    @Test
    fun test() = runBlocking {
        // Check empty
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe 0L
        localDataStore.listAsFlow().first() shouldBe listOf()
        // Check save
        val firstTime = parseUTC("2021-11-15T01:12:45Z")
        val secondTime = parseUTC("2021-11-15T03:00:00Z")
        every {
            currentTimeGetter.get()
        } returns firstTime.time andThen secondTime.time
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
        localDataStore.save(listOf(repo1, repo2, repo3))
        localDataStore.listAsFlow().first() shouldBe listOf(repo1, repo3, repo2)
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe firstTime.time
        // Second save
        localDataStore.save(listOf(repo1.copy(name = "Edited"), repo2, repo3))
        localDataStore.listAsFlow().first() shouldBe listOf(
            repo1.copy(name = "Edited"),
            repo3,
            repo2
        )
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe secondTime.time
        // Check clear
        localDataStore.clear()
        createdLocalDataStore.get(LocalCreated.KIND_GITHUB_REPO) shouldBe 0
    }
}
