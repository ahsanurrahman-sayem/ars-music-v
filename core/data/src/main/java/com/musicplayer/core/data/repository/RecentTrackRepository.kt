package com.musicplayer.core.data.repository

import com.musicplayer.core.model.RecentTrack
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for recently played track operations.
 */
interface RecentTrackRepository {

    /** Get recently played tracks ordered by last played time */
    fun getRecentTracks(limit: Int = 100): Flow<List<RecentTrack>>

    /** Record a track play event */
    suspend fun recordPlay(trackId: String, positionMs: Long, completionRate: Float)

    /** Remove a specific recent track entry */
    suspend fun removeRecentTrack(id: String)

    /** Clear all recent track history */
    suspend fun clearAll()

    /** Get total count of recent tracks */
    fun getRecentTrackCount(): Flow<Int>
}
