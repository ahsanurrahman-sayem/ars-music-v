package com.musicplayer.core.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Represents a user-created playlist.
 * Contains only metadata - track references are stored in PlaylistTrackCrossRef.
 */
@Entity(
    tableName = "playlists",
    indices = [Index(value = ["name"])]
)
@Serializable
data class Playlist(
    @PrimaryKey
    val id: String,
    /** Playlist display name */
    val name: String,
    /** Optional user description */
    val description: String? = null,
    /** Optional custom cover image URI */
    val coverUri: String? = null,
    /** Creation timestamp */
    val dateCreated: Long = System.currentTimeMillis(),
    /** Last modified timestamp */
    val dateModified: Long = System.currentTimeMillis()
)
