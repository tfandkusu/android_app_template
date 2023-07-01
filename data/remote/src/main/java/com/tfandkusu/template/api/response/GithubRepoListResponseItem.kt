package com.tfandkusu.template.api.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRepoListResponseItem(
    val id: Long,
    val name: String,
    val description: String?,
    @SerialName("updated_at")
    val updatedAt: Instant,
    val language: String?,
    @SerialName("html_url")
    val htmlUrl: String,
    val fork: Boolean
)
