package com.musicplayer.core.domain.usecase.track

import android.content.Context
import android.net.Uri
import com.musicplayer.core.common.AudioMetadataExtractor
import com.musicplayer.core.common.FileUriUtils
import com.musicplayer.core.common.IdGenerator
import com.musicplayer.core.data.repository.TrackRepository
import com.musicplayer.core.model.Track
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use case to add a track to the library from a content URI.
 * Extracts metadata from the audio file and stores only the URI reference.
 */
class AddTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(uri: Uri): Result<Track> = withContext(Dispatchers.IO) {
        try {
            // Take persistable permission
            FileUriUtils.takePersistablePermission(context, uri)

            // Extract metadata
            val metadata = AudioMetadataExtractor.extractMetadata(context, uri)
            val fileMetadata = FileUriUtils.getFileMetadata(context, uri)

            val track = Track(
                id = IdGenerator.generateFromSeed(uri.toString()),
                uri = uri.toString(),
                title = metadata.title,
                artist = metadata.artist,
                album = metadata.album,
                durationMs = metadata.durationMs,
                size = fileMetadata.size,
                mimeType = metadata.mimeType
            )

            trackRepository.addTrack(track)
            Result.success(track)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addMultiple(uris: List<Uri>): List<Result<Track>> = withContext(Dispatchers.IO) {
        uris.map { uri ->
            invoke(uri)
        }
    }
}
