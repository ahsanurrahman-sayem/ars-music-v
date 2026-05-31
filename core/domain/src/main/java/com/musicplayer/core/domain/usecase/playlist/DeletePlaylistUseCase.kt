package com.musicplayer.core.domain.usecase.playlist

import com.musicplayer.core.data.repository.PlaylistRepository
import com.musicplayer.core.model.Playlist
import javax.inject.Inject

/**
 * Use case to delete a playlist.
 */
class DeletePlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist) {
        playlistRepository.deletePlaylist(playlist)
    }
}
