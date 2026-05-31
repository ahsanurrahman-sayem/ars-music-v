package com.musicplayer.core.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents an item in the playback queue.
 * Queue is ephemeral - it doesn't survive app restart unless explicitly saved as a playlist.
 */
@Entity(
    tableName = "queue_items",
    foreignKeys = [
        ForeignKey(
            entity = Track::class,
            parentColumns = ["id"],
            childColumns = ["trackId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["trackId"]), Index(value = ["position"])]
)
data class QueueItem(
    @PrimaryKey
    val id: String,
    /** Reference to the track */
    val trackId: String,
    /** Position in queue (0-based) */
    val position: Int,
    /** Unique session identifier to distinguish queue sessions */
    val sessionId: String
)
