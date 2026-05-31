package com.musicplayer.core.domain.usecase.track

import com.musicplayer.core.data.repository.TrackRepository
import javax.inject.Inject

/**
 * Use case to toggle favorite status of a track.
 */
class ToggleFavoriteUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(trackId: String) {
        trackRepository.toggleFavorite(trackId)
    }
}
