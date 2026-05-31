package com.musicplayer.core.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Tracks recently played songs with playback position.
 * Maintains history of playback for quick resume.
 */
@Entity(
    tableName = "recent_tracks",
    foreignKeys = [
        ForeignKey(
            entity = Track::class,
            parentColumns = ["id"],
            childColumns = ["trackId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["trackId"], unique = true),
        Index(value = ["lastPlayed"])
    ]
)
data class RecentTrack(
    @PrimaryKey
    val id: String,
    /** Reference to the track */
    val trackId: String,
    /** Last playback position in milliseconds */
    val lastPositionMs: Long = 0,
    /** Timestamp when the track was last played */
    val lastPlayed: Long = System.currentTimeMillis(),
    /** Total number of times this track has been played */
    val playCount: Int = 1,
    /** Completion percentage (0.0 to 1.0) of last playback */
    val completionRate: Float = 0f
)
