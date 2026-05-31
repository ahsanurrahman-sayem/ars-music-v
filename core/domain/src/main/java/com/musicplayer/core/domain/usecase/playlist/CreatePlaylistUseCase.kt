package com.musicplayer.core.domain.usecase.playlist

import com.musicplayer.core.data.repository.PlaylistRepository
import javax.inject.Inject

/**
 * Use case to create a new playlist.
 */
class CreatePlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(name: String, description: String? = null) {
        require(name.isNotBlank()) { "Playlist name cannot be blank" }
        playlistRepository.createPlaylist(name.trim(), description?.trim())
    }
}
