package com.tfandkusu.template.api.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.util.Date

@Keep
data class GithubRepoListResponseItem(
    val id: Long,
    val name: String,
    val description: String?,
    @field:Json(name = "updated_at")
    val updatedAt: Date,
    val language: String?,
    @field:Json(name = "html_url")
    val htmlUrl: String,
    val fork: Boolean
)
