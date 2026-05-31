package com.musicplayer.core.data.repository

import com.musicplayer.core.common.IdGenerator
import com.musicplayer.core.data.dao.RecentTrackDao
import com.musicplayer.core.model.RecentTrack
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of RecentTrackRepository using Room database.
 */
@Singleton
class RecentTrackRepositoryImpl @Inject constructor(
    private val recentTrackDao: RecentTrackDao
) : RecentTrackRepository {

    override fun getRecentTracks(limit: Int): Flow<List<RecentTrack>> =
        recentTrackDao.getRecentTracks(limit)

    override suspend fun recordPlay(trackId: String, positionMs: Long, completionRate: Float) {
        val existing = recentTrackDao.getRecentTrackByTrackId(trackId)

        if (existing != null) {
            // Update existing entry
            val updated = existing.copy(
                lastPositionMs = positionMs,
                lastPlayed = System.currentTimeMillis(),
                playCount = existing.playCount + 1,
                completionRate = completionRate
            )
            recentTrackDao.update(updated)
        } else {
            // Create new entry
            val recentTrack = RecentTrack(
                id = IdGenerator.generate(),
                trackId = trackId,
                lastPositionMs = positionMs,
                lastPlayed = System.currentTimeMillis(),
                playCount = 1,
                completionRate = completionRate
            )
            recentTrackDao.insert(recentTrack)
        }
    }

    override suspend fun removeRecentTrack(id: String) {
        recentTrackDao.deleteById(id)
    }

    override suspend fun clearAll() {
        recentTrackDao.clearAll()
    }

    override fun getRecentTrackCount(): Flow<Int> = recentTrackDao.getRecentTrackCount()
}
