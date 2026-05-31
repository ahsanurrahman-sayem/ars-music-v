package com.musicplayer.core.data.repository

import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for track operations.
 * Abstracts the data source from the domain layer.
 */
interface TrackRepository {

    /** Get all tracks in the library sorted by title */
    fun getAllTracks(): Flow<List<Track>>

    /** Get a single track by ID */
    fun getTrackById(trackId: String): Flow<Track?>

    /** Get track by URI */
    suspend fun getTrackByUri(uri: String): Track?

    /** Get all favorite tracks */
    fun getFavoriteTracks(): Flow<List<Track>>

    /** Add a new track to the library */
    suspend fun addTrack(track: Track)

    /** Add multiple tracks to the library */
    suspend fun addTracks(tracks: List<Track>)

    /** Remove a track from the library */
    suspend fun removeTrack(track: Track)

    /** Remove a track by ID */
    suspend fun removeTrackById(trackId: String)

    /** Toggle favorite status of a track */
    suspend fun toggleFavorite(trackId: String)

    /** Set favorite status explicitly */
    suspend fun setFavorite(trackId: String, isFavorite: Boolean)

    /** Set custom cover art URI for a track */
    suspend fun setCustomCover(trackId: String, coverUri: String?)

    /** Search tracks by title, artist, or album */
    fun searchTracks(query: String): Flow<List<Track>>

    /** Check if a track exists in the library */
    suspend fun trackExists(trackId: String): Boolean

    /** Get total track count */
    fun getTrackCount(): Flow<Int>
}
