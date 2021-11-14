package com.tfandkusu.template.api

import com.tfandkusu.template.api.response.GithubRepoListResponseItem
import retrofit2.http.GET

interface TemplateApiService {
    @GET("/users/tfandkusu/repos")
    suspend fun listRepos(): List<GithubRepoListResponseItem>
}
