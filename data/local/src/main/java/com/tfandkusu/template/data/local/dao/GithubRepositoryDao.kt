package com.tfandkusu.template.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tfandkusu.template.data.local.entity.LocalGithubRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubRepositoryDao {
    @Query("SELECT * FROM githubRepository ORDER BY updatedAt DESC")
    fun listAsFlow(): Flow<List<LocalGithubRepository>>

    @Insert
    suspend fun insert(localGithubRepository: LocalGithubRepository)

    @Query("DELETE FROM githubRepository")
    suspend fun clear()
}
