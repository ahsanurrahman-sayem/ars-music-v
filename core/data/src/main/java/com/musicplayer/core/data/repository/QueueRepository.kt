package com.musicplayer.core.data.repository

import com.musicplayer.core.model.QueueItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for queue operations.
 * Queue is ephemeral and tied to a session ID.
 */
interface QueueRepository {

    /** Get current queue for a session */
    fun getQueue(sessionId: String): Flow<List<QueueItem>>

    /** Set the entire queue for a session */
    suspend fun setQueue(sessionId: String, trackIds: List<String>)

    /** Add a single track to the end of the queue */
    suspend fun addToQueue(sessionId: String, trackId: String)

    /** Remove a track from the queue by item ID */
    suspend fun removeFromQueue(itemId: String)

    /** Move a queue item to a new position */
    suspend fun moveItem(sessionId: String, itemId: String, newPosition: Int)

    /** Clear all items for a session */
    suspend fun clearSession(sessionId: String)

    /** Clear all queue data */
    suspend fun clearAll()

    /** Get queue size for a session */
    fun getQueueSize(sessionId: String): Flow<Int>
}
