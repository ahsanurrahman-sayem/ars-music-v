package com.musicplayer.core.data.repository

import com.musicplayer.core.model.Playlist
import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for playlist operations.
 */
interface PlaylistRepository {

    /** Get all playlists ordered by creation date */
    fun getAllPlaylists(): Flow<List<Playlist>>

    /** Get a specific playlist by ID */
    fun getPlaylistById(playlistId: String): Flow<Playlist?>

    /** Create a new playlist */
    suspend fun createPlaylist(name: String, description: String? = null)

    /** Update an existing playlist */
    suspend fun updatePlaylist(playlist: Playlist)

    /** Delete a playlist */
    suspend fun deletePlaylist(playlist: Playlist)

    /** Add a track to a playlist */
    suspend fun addTrackToPlaylist(playlistId: String, trackId: String)

    /** Add multiple tracks to a playlist */
    suspend fun addTracksToPlaylist(playlistId: String, trackIds: List<String>)

    /** Remove a track from a playlist */
    suspend fun removeTrackFromPlaylist(playlistId: String, trackId: String)

    /** Move a track to a new position in a playlist */
    suspend fun reorderTrack(playlistId: String, trackId: String, newPosition: Int)

    /** Get all tracks in a playlist ordered by position */
    fun getTracksInPlaylist(playlistId: String): Flow<List<Track>>

    /** Get track count in a playlist */
    fun getPlaylistTrackCount(playlistId: String): Flow<Int>

    /** Get total playlist count */
    fun getPlaylistCount(): Flow<Int>
}
