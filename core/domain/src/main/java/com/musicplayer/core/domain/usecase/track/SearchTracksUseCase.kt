package com.musicplayer.core.domain.usecase.track

import com.musicplayer.core.data.repository.TrackRepository
import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to search tracks by query string.
 */
class SearchTracksUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    operator fun invoke(query: String): Flow<List<Track>> =
        trackRepository.searchTracks(query.trim())
}
