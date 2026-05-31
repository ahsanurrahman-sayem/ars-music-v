package com.musicplayer.core.data.repository

import com.musicplayer.core.data.dao.TrackDao
import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of TrackRepository using Room database.
 */
@Singleton
class TrackRepositoryImpl @Inject constructor(
    private val trackDao: TrackDao
) : TrackRepository {

    override fun getAllTracks(): Flow<List<Track>> = trackDao.getAllTracks()

    override fun getTrackById(trackId: String): Flow<Track?> = trackDao.getTrackById(trackId)

    override suspend fun getTrackByUri(uri: String): Track? = trackDao.getTrackByUri(uri)

    override fun getFavoriteTracks(): Flow<List<Track>> = trackDao.getFavoriteTracks()

    override suspend fun addTrack(track: Track) = trackDao.insert(track)

    override suspend fun addTracks(tracks: List<Track>) = trackDao.insertAll(tracks)

    override suspend fun removeTrack(track: Track) = trackDao.delete(track)

    override suspend fun removeTrackById(trackId: String) = trackDao.deleteById(trackId)

    override suspend fun toggleFavorite(trackId: String) {
        val track = trackDao.getTrackById(trackId)
        track.collect { currentTrack ->
            if (currentTrack != null) {
                trackDao.setFavorite(trackId, !currentTrack.isFavorite)
            }
        }
    }

    override suspend fun setFavorite(trackId: String, isFavorite: Boolean) {
        trackDao.setFavorite(trackId, isFavorite)
    }

    override suspend fun setCustomCover(trackId: String, coverUri: String?) {
        trackDao.setCustomCover(trackId, coverUri)
    }

    override fun searchTracks(query: String): Flow<List<Track>> {
        return if (query.isBlank()) {
            trackDao.getAllTracks()
        } else {
            trackDao.searchTracks(query)
        }
    }

    override suspend fun trackExists(trackId: String): Boolean = trackDao.trackExists(trackId)

    override fun getTrackCount(): Flow<Int> = trackDao.getTrackCount()
}
