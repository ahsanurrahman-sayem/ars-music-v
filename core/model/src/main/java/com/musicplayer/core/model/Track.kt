package com.musicplayer.core.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Represents a music track in the library.
 * Stores only metadata and URI reference - never duplicates audio files.
 */
@Entity(
    tableName = "tracks",
    indices = [
        Index(value = ["uri"], unique = true),
        Index(value = ["title"]),
        Index(value = ["artist"])
    ]
)
@Serializable
data class Track(
    @PrimaryKey
    val id: String,
    /** Content URI pointing to the original audio file */
    val uri: String,
    /** Display name of the file */
    val title: String,
    /** Track artist (may be empty if not available) */
    val artist: String,
    /** Track album (may be empty) */
    val album: String,
    /** Duration in milliseconds */
    val durationMs: Long,
    /** File size in bytes */
    val size: Long,
    /** MIME type of the audio file */
    val mimeType: String,
    /** Optional custom cover art URI chosen by the user */
    val customCoverUri: String? = null,
    /** Whether this track is marked as favorite */
    val isFavorite: Boolean = false,
    /** Timestamp when the track was added to library */
    val dateAdded: Long = System.currentTimeMillis(),
    /** User-provided notes or metadata */
    val userNotes: String? = null
) {
    companion object {
        const val UNKNOWN = "<unknown>"
    }
}
