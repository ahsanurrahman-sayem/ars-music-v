package com.musicplayer.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.musicplayer.core.model.RecentTrack
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for RecentTrack entity.
 * Manages the recently played track history.
 */
@Dao
interface RecentTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentTrack: RecentTrack)

    @Update
    suspend fun update(recentTrack: RecentTrack)

    @Query("DELETE FROM recent_tracks WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM recent_tracks")
    suspend fun clearAll()

    @Query("""
        SELECT rt.* FROM recent_tracks rt
        ORDER BY rt.lastPlayed DESC
        LIMIT :limit
    """)
    fun getRecentTracks(limit: Int = 100): Flow<List<RecentTrack>>

    @Query("SELECT * FROM recent_tracks WHERE trackId = :trackId")
    suspend fun getRecentTrackByTrackId(trackId: String): RecentTrack?

    @Query("SELECT COUNT(*) FROM recent_tracks")
    fun getRecentTrackCount(): Flow<Int>

    @Query("DELETE FROM recent_tracks WHERE lastPlayed < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)
}
