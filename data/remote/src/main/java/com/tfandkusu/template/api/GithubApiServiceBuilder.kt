package com.tfandkusu.template.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.MediaType
import retrofit2.Retrofit

object GithubApiServiceBuilder {
    @OptIn(ExperimentalSerializationApi::class)
    fun build(): GithubApiService {
        val json = Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
        return retrofit.create(GithubApiService::class.java)
    }
}
