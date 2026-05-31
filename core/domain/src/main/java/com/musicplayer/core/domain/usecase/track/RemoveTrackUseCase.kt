package com.musicplayer.core.domain.usecase.track

import com.musicplayer.core.data.repository.TrackRepository
import com.musicplayer.core.model.Track
import javax.inject.Inject

/**
 * Use case to remove a track from the library.
 */
class RemoveTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(track: Track) {
        trackRepository.removeTrack(track)
    }

    suspend fun byId(trackId: String) {
        trackRepository.removeTrackById(trackId)
    }
}
