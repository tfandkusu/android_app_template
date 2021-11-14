package com.tfandkusu.template.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tfandkusu.template.data.local.dao.CreatedDao
import com.tfandkusu.template.data.local.dao.GithubRepositoryDao
import com.tfandkusu.template.data.local.entity.LocalCreated
import com.tfandkusu.template.data.local.entity.LocalGithubRepository

@Database(entities = [LocalGithubRepository::class, LocalCreated::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubRepositoryDao(): GithubRepositoryDao

    abstract fun createdDao(): CreatedDao
}
