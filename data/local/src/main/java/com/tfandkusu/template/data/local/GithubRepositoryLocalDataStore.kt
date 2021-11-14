package com.tfandkusu.template.data.local

import androidx.room.withTransaction
import com.tfandkusu.template.data.local.db.AppDatabase
import com.tfandkusu.template.data.local.entity.LocalCreated
import com.tfandkusu.template.data.local.entity.LocalGithubRepository
import com.tfandkusu.template.model.GithubRepository
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GithubRepositoryLocalDataStore {
    suspend fun save(githubRepositories: List<GithubRepository>)

    fun listAsFlow(): Flow<List<GithubRepository>>
}

class GithubRepositoryLocalDataStoreImpl(private val db: AppDatabase) :
    GithubRepositoryLocalDataStore {

    private val githubRepositoryDao = db.githubRepositoryDao()

    private val createdDao = db.createdDao()

    override suspend fun save(githubRepositories: List<GithubRepository>) {
        db.withTransaction {
            githubRepositoryDao.clear()
            githubRepositories.map {
                LocalGithubRepository(
                    0L,
                    it.id,
                    it.name,
                    it.description,
                    it.updatedAt.time,
                    it.language,
                    it.htmlUrl,
                    it.forked
                )
            }.map {
                githubRepositoryDao.insert(it)
            }
            createdDao.insert(LocalCreated(0L, LocalCreated.KIND_GITHUB_REPOSITORY, 0L))
        }
    }

    override fun listAsFlow() = githubRepositoryDao.listAsFlow().map { list ->
        list.map {
            GithubRepository(
                it.serverId,
                it.name,
                it.description,
                Date(it.updatedAt),
                it.language,
                it.htmlUrl,
                it.forked
            )
        }
    }
}
