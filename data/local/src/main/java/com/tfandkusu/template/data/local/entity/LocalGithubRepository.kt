package com.tfandkusu.template.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "githubRepository")
data class LocalGithubRepository(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val serverId: Long,
    val name: String,
    val description: String,
    @ColumnInfo(index = true)
    val updatedAt: Long,
    val language: String,
    val htmlUrl: String,
    val forked: Boolean
)
