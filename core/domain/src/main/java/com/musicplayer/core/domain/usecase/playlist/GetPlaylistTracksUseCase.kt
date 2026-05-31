package com.musicplayer.core.domain.usecase.playlist

import com.musicplayer.core.data.repository.PlaylistRepository
import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get all tracks in a specific playlist.
 */
class GetPlaylistTracksUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(playlistId: String): Flow<List<Track>> =
        playlistRepository.getTracksInPlaylist(playlistId)
}
