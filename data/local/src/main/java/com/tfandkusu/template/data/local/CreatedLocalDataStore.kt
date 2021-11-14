package com.tfandkusu.template.data.local

import com.tfandkusu.template.data.local.db.AppDatabase

/**
 * Local data store to save time for cache.
 */
interface CreatedLocalDataStore {
    /**
     * Get save time from kind parameter
     */
    suspend fun get(kind: String): Long
}

class CreatedLocalDataStoreImpl(db: AppDatabase) : CreatedLocalDataStore {

    private val dao = db.createdDao()

    override suspend fun get(kind: String) = dao.get(kind)?.createdAt ?: 0L
}
