package com.musicplayer.feature.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.core.data.repository.TrackRepository
import com.musicplayer.core.domain.usecase.playlist.PlaybackController
import com.musicplayer.core.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueueViewModel @Inject constructor(
    private val playbackController: PlaybackController,
    trackRepository: TrackRepository
) : ViewModel() {

    // Since queue is managed by the player controller internally,
    // we'll show the current track list as the queue
    val queueTracks: StateFlow<List<Track>> = trackRepository.getAllTracks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val currentTrackId: StateFlow<String?> = playbackController.playbackState
        .map { it.currentTrack?.id }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun playTrackAtIndex(index: Int) {
        viewModelScope.launch {
            val tracks = queueTracks.value
            if (index in tracks.indices) {
                playbackController.setQueue(tracks, index)
            }
        }
    }

    fun removeFromQueue(index: Int) {
        viewModelScope.launch {
            playbackController.removeFromQueue(index)
        }
    }

    fun clearQueue() {
        // Queue cannot be fully cleared while playing
        // This would just clear any upcoming tracks
    }
}
