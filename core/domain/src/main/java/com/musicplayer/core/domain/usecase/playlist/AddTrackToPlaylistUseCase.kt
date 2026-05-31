package com.musicplayer.core.domain.usecase.playlist

import com.musicplayer.core.data.repository.PlaylistRepository
import javax.inject.Inject

/**
 * Use case to add a track to a playlist.
 */
class AddTrackToPlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: String, trackId: String) {
        playlistRepository.addTrackToPlaylist(playlistId, trackId)
    }

    suspend fun addMultiple(playlistId: String, trackIds: List<String>) {
        playlistRepository.addTracksToPlaylist(playlistId, trackIds)
    }
}
