package com.musicplayer.feature.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.core.data.repository.RecentTrackRepository
import com.musicplayer.core.data.repository.TrackRepository
import com.musicplayer.core.model.RecentTrack
import com.musicplayer.core.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI model combining RecentTrack with its associated Track details.
 */
data class RecentTrackWithDetails(
    val id: String,
    val track: Track?,
    val lastPlayed: Long,
    val playCount: Int,
    val completionRate: Float
)

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val recentTrackRepository: RecentTrackRepository,
    trackRepository: TrackRepository
) : ViewModel() {

    val recentTracks: StateFlow<List<RecentTrackWithDetails>> =
        recentTrackRepository.getRecentTracks(100)
            .combine(trackRepository.getAllTracks()) { recentTracks, allTracks ->
                val trackMap = allTracks.associateBy { it.id }
                recentTracks.map { recent ->
                    RecentTrackWithDetails(
                        id = recent.id,
                        track = trackMap[recent.trackId],
                        lastPlayed = recent.lastPlayed,
                        playCount = recent.playCount,
                        completionRate = recent.completionRate
                    )
                }.filter { it.track != null }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun clearAll() {
        viewModelScope.launch {
            recentTrackRepository.clearAll()
        }
    }
}
