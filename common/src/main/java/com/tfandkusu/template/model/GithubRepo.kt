package com.tfandkusu.template.model

import kotlinx.datetime.Instant

data class GithubRepo(
    val id: Long,
    val name: String,
    val description: String,
    val updatedAt: Instant,
    val language: String,
    val htmlUrl: String,
    val fork: Boolean,
    val favorite: Boolean
)
