package com.musicplayer.feature.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.core.domain.usecase.playlist.PlaybackController
import com.musicplayer.core.model.RepeatMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Player screen.
 */
data class PlayerUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the Player screen.
 * Manages playback controls and state observation.
 */
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playbackController: PlaybackController
) : ViewModel() {

    val playbackState = playbackController.playbackState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = com.musicplayer.core.model.PlaybackState()
        )

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun togglePlayPause() {
        viewModelScope.launch {
            playbackController.togglePlayPause()
        }
    }

    fun skipToNext() {
        viewModelScope.launch {
            playbackController.skipToNext()
        }
    }

    fun skipToPrevious() {
        viewModelScope.launch {
            playbackController.skipToPrevious()
        }
    }

    fun seekTo(positionMs: Long) {
        viewModelScope.launch {
            playbackController.seekTo(positionMs)
        }
    }

    fun toggleShuffle() {
        viewModelScope.launch {
            val currentShuffle = playbackState.value.isShuffleOn
            playbackController.setShuffle(!currentShuffle)
        }
    }

    fun cycleRepeatMode() {
        viewModelScope.launch {
            val nextMode = when (playbackState.value.repeatMode) {
                RepeatMode.OFF -> RepeatMode.ALL
                RepeatMode.ALL -> RepeatMode.ONE
                RepeatMode.ONE -> RepeatMode.OFF
            }
            playbackController.setRepeatMode(nextMode)
        }
    }
}
