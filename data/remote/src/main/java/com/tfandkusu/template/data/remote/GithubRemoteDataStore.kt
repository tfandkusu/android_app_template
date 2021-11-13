package com.tfandkusu.template.data.remote

import com.tfandkusu.template.api.TemplateApiService
import com.tfandkusu.template.model.GithubRepository

interface GithubRemoteDataStore {
    suspend fun listRepositories(): List<GithubRepository>
}

class GithubRemoteDataStoreImpl(
    private val service: TemplateApiService
) : GithubRemoteDataStore {
    override suspend fun listRepositories(): List<GithubRepository> {
        val response = service.listRepositories()
        return response.map {
            GithubRepository(
                it.id,
                it.name,
                it.description ?: "",
                it.updatedAt,
                it.language ?: "",
                it.htmlUrl,
                it.forked
            )
        }
        // TODO Error
    }
}
