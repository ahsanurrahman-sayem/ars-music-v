package com.musicplayer.core.domain.usecase.playlist

import com.musicplayer.core.data.repository.PlaylistRepository
import com.musicplayer.core.model.Playlist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get all playlists.
 */
class GetAllPlaylistsUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {
    operator fun invoke(): Flow<List<Playlist>> = playlistRepository.getAllPlaylists()
}
