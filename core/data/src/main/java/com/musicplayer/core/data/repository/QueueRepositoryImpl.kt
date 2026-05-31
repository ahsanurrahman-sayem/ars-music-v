package com.musicplayer.core.data.repository

import com.musicplayer.core.common.IdGenerator
import com.musicplayer.core.data.dao.QueueDao
import com.musicplayer.core.model.QueueItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of QueueRepository using Room database.
 */
@Singleton
class QueueRepositoryImpl @Inject constructor(
    private val queueDao: QueueDao
) : QueueRepository {

    override fun getQueue(sessionId: String): Flow<List<QueueItem>> =
        queueDao.getQueueForSession(sessionId)

    override suspend fun setQueue(sessionId: String, trackIds: List<String>) {
        // Clear existing queue for this session
        queueDao.clearSession(sessionId)
        // Insert new queue items
        val items = trackIds.mapIndexed { index, trackId ->
            QueueItem(
                id = IdGenerator.generate(),
                trackId = trackId,
                position = index,
                sessionId = sessionId
            )
        }
        queueDao.insertAll(items)
    }

    override suspend fun addToQueue(sessionId: String, trackId: String) {
        val currentQueue = queueDao.getQueueForSessionSync(sessionId)
        val newPosition = if (currentQueue.isEmpty()) 0 else currentQueue.maxOf { it.position } + 1
        val item = QueueItem(
            id = IdGenerator.generate(),
            trackId = trackId,
            position = newPosition,
            sessionId = sessionId
        )
        queueDao.insert(item)
    }

    override suspend fun removeFromQueue(itemId: String) {
        queueDao.deleteById(itemId)
    }

    override suspend fun moveItem(sessionId: String, itemId: String, newPosition: Int) {
        queueDao.updatePosition(itemId, newPosition)
    }

    override suspend fun clearSession(sessionId: String) {
        queueDao.clearSession(sessionId)
    }

    override suspend fun clearAll() {
        queueDao.clearAll()
    }

    override fun getQueueSize(sessionId: String): Flow<Int> =
        queueDao.getQueueSize(sessionId)
}
