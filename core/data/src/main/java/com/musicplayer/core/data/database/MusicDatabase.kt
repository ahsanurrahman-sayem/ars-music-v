package com.musicplayer.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.musicplayer.core.data.dao.PlaylistDao
import com.musicplayer.core.data.dao.QueueDao
import com.musicplayer.core.data.dao.RecentTrackDao
import com.musicplayer.core.data.dao.TrackDao
import com.musicplayer.core.model.Playlist
import com.musicplayer.core.model.PlaylistTrackCrossRef
import com.musicplayer.core.model.QueueItem
import com.musicplayer.core.model.RecentTrack
import com.musicplayer.core.model.Track

/**
 * Room database for the music player application.
 * Contains all entity tables and provides DAO access.
 *
 * Database version: 1
 * Entities: Track, Playlist, PlaylistTrackCrossRef, QueueItem, RecentTrack
 */
@Database(
    entities = [
        Track::class,
        Playlist::class,
        PlaylistTrackCrossRef::class,
        QueueItem::class,
        RecentTrack::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun queueDao(): QueueDao
    abstract fun recentTrackDao(): RecentTrackDao

    companion object {
        const val DATABASE_NAME = "music_player.db"
    }
}
