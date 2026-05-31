package com.musicplayer.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.musicplayer.core.model.QueueItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for QueueItem entity.
 * Manages the playback queue which is ephemeral (not persisted across sessions by default).
 */
@Dao
interface QueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: QueueItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<QueueItem>)

    @Update
    suspend fun update(item: QueueItem)

    @Query("DELETE FROM queue_items WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("DELETE FROM queue_items WHERE sessionId = :sessionId")
    suspend fun clearSession(sessionId: String)

    @Query("DELETE FROM queue_items")
    suspend fun clearAll()

    @Query("SELECT * FROM queue_items WHERE sessionId = :sessionId ORDER BY position ASC")
    fun getQueueForSession(sessionId: String): Flow<List<QueueItem>>

    @Query("SELECT * FROM queue_items WHERE sessionId = :sessionId ORDER BY position ASC")
    suspend fun getQueueForSessionSync(sessionId: String): List<QueueItem>

    @Query("SELECT COUNT(*) FROM queue_items WHERE sessionId = :sessionId")
    fun getQueueSize(sessionId: String): Flow<Int>

    @Query("UPDATE queue_items SET position = position - 1 WHERE sessionId = :sessionId AND position > :removedPosition")
    suspend fun decrementPositionsAfter(sessionId: String, removedPosition: Int)

    @Query("UPDATE queue_items SET position = :newPosition WHERE id = :itemId")
    suspend fun updatePosition(itemId: String, newPosition: Int)
}
