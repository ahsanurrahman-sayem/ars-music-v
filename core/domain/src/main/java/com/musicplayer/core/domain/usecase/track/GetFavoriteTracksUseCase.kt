package com.musicplayer.core.domain.usecase.track

import com.musicplayer.core.data.repository.TrackRepository
import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get all favorite tracks.
 */
class GetFavoriteTracksUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    operator fun invoke(): Flow<List<Track>> = trackRepository.getFavoriteTracks()
}
