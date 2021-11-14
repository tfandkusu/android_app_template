package com.tfandkusu.template.data.local.db

import android.content.Context
import androidx.room.Room

object AppDatabaseBuilder {
    fun build(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()
    }
}
