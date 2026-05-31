package com.musicplayer.core.common

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Extracts audio metadata from audio files using MediaMetadataRetriever.
 * Runs on IO dispatcher to avoid blocking the main thread.
 */
object AudioMetadataExtractor {

    /**
     * Extract metadata from an audio file URI.
     * Returns a map of standard metadata fields.
     */
    suspend fun extractMetadata(context: Context, uri: Uri): AudioMetadata = withContext(Dispatchers.IO) {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, uri)

            val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                ?: FileUriUtils.getBaseName(
                    FileUriUtils.getFileMetadata(context, uri).displayName
                )
            val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                ?: retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
                ?: "Unknown Artist"
            val album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
                ?: "Unknown Album"
            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                ?: "0"
            val duration = durationStr.toLongOrNull() ?: 0L
            val mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
                ?: "audio/*"

            AudioMetadata(
                title = title,
                artist = artist,
                album = album,
                durationMs = duration,
                mimeType = mimeType
            )
        } catch (e: Exception) {
            // Fallback: use file metadata only
            val fileMeta = FileUriUtils.getFileMetadata(context, uri)
            AudioMetadata(
                title = FileUriUtils.getBaseName(fileMeta.displayName),
                artist = "Unknown Artist",
                album = "Unknown Album",
                durationMs = 0L,
                mimeType = fileMeta.mimeType
            )
        } finally {
            retriever.release()
        }
    }

    /**
     * Extract embedded artwork from an audio file.
     * Returns byte array of artwork or null if not available.
     */
    suspend fun extractArtwork(context: Context, uri: Uri): ByteArray? = withContext(Dispatchers.IO) {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, uri)
            retriever.embeddedPicture
        } catch (e: Exception) {
            null
        } finally {
            retriever.release()
        }
    }

    /**
     * Get duration of an audio file in milliseconds.
     */
    suspend fun getDuration(context: Context, uri: Uri): Long = withContext(Dispatchers.IO) {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, uri)
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                ?.toLongOrNull() ?: 0L
        } catch (e: Exception) {
            0L
        } finally {
            retriever.release()
        }
    }
}

/**
 * Extracted audio metadata.
 */
data class AudioMetadata(
    val title: String,
    val artist: String,
    val album: String,
    val durationMs: Long,
    val mimeType: String
)
