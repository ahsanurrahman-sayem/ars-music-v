package com.musicplayer.core.data.repository

import com.musicplayer.core.common.IdGenerator
import com.musicplayer.core.data.dao.PlaylistDao
import com.musicplayer.core.model.Playlist
import com.musicplayer.core.model.PlaylistTrackCrossRef
import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of PlaylistRepository using Room database.
 */
@Singleton
class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {

    override fun getAllPlaylists(): Flow<List<Playlist>> = playlistDao.getAllPlaylists()

    override fun getPlaylistById(playlistId: String): Flow<Playlist?> =
        playlistDao.getPlaylistById(playlistId)

    override suspend fun createPlaylist(name: String, description: String?) {
        val playlist = Playlist(
            id = IdGenerator.generate(),
            name = name,
            description = description
        )
        playlistDao.insertPlaylist(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        val updated = playlist.copy(dateModified = System.currentTimeMillis())
        playlistDao.updatePlaylist(updated)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.deletePlaylist(playlist)
    }

    override suspend fun addTrackToPlaylist(playlistId: String, trackId: String) {
        val lastPosition = playlistDao.getLastPosition(playlistId) ?: -1
        val crossRef = PlaylistTrackCrossRef(
            playlistId = playlistId,
            trackId = trackId,
            position = lastPosition + 1
        )
        playlistDao.addTrackToPlaylist(crossRef)
    }

    override suspend fun addTracksToPlaylist(playlistId: String, trackIds: List<String>) {
        val lastPosition = playlistDao.getLastPosition(playlistId) ?: -1
        val crossRefs = trackIds.mapIndexed { index, trackId ->
            PlaylistTrackCrossRef(
                playlistId = playlistId,
                trackId = trackId,
                position = lastPosition + 1 + index
            )
        }
        playlistDao.addTracksToPlaylist(crossRefs)
    }

    override suspend fun removeTrackFromPlaylist(playlistId: String, trackId: String) {
        playlistDao.removeTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun reorderTrack(playlistId: String, trackId: String, newPosition: Int) {
        playlistDao.updateTrackPosition(playlistId, trackId, newPosition)
    }

    override fun getTracksInPlaylist(playlistId: String): Flow<List<Track>> =
        playlistDao.getTracksInPlaylist(playlistId)

    override fun getPlaylistTrackCount(playlistId: String): Flow<Int> =
        playlistDao.getPlaylistTrackCount(playlistId)

    override fun getPlaylistCount(): Flow<Int> = playlistDao.getPlaylistCount()
}
