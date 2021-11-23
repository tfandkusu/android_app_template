package com.tfandkusu.template.data.remote

import com.tfandkusu.template.api.TemplateApiService
import com.tfandkusu.template.error.mapApiError
import com.tfandkusu.template.model.GithubRepo
import javax.inject.Inject

interface GithubRemoteDataStore {
    suspend fun listRepositories(): List<GithubRepo>
}

class GithubRemoteDataStoreImpl @Inject constructor(
    private val service: TemplateApiService
) : GithubRemoteDataStore {
    override suspend fun listRepositories(): List<GithubRepo> {
        try {
            val response = service.listRepos()
            return response.map {
                GithubRepo(
                    it.id,
                    it.name,
                    it.description ?: "",
                    it.updatedAt,
                    it.language ?: "",
                    it.htmlUrl,
                    it.forked
                )
            }
        } catch (e: Throwable) {
            throw mapApiError(e)
        }
    }
}
