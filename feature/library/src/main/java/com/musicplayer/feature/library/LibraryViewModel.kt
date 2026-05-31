package com.musicplayer.feature.library

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.core.common.Result
import com.musicplayer.core.domain.usecase.playlist.PlaybackController
import com.musicplayer.core.domain.usecase.playlist.SetQueueUseCase
import com.musicplayer.core.domain.usecase.track.AddTrackUseCase
import com.musicplayer.core.domain.usecase.track.GetAllTracksUseCase
import com.musicplayer.core.domain.usecase.track.SearchTracksUseCase
import com.musicplayer.core.model.PlaybackState
import com.musicplayer.core.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Library screen.
 */
data class LibraryUiState(
    val tracks: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val isSearchActive: Boolean = false,
    val error: String? = null,
    val currentTrack: Track? = null,
    val isPlaying: Boolean = false
)

/**
 * ViewModel for the Library screen.
 * Manages track list, search, import, and playback initiation.
 */
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getAllTracksUseCase: GetAllTracksUseCase,
    private val searchTracksUseCase: SearchTracksUseCase,
    private val addTrackUseCase: AddTrackUseCase,
    private val playbackController: PlaybackController,
    private val setQueueUseCase: SetQueueUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())

    val uiState: StateFlow<LibraryUiState> = combine(
        _tracks,
        _isSearchActive,
        searchQuery,
        playbackController.playbackState
    ) { tracks, isSearchActive, query, playbackState ->
        val filteredTracks = if (isSearchActive && query.isNotBlank()) {
            tracks.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.artist.contains(query, ignoreCase = true) ||
                it.album.contains(query, ignoreCase = true)
            }
        } else {
            tracks
        }

        LibraryUiState(
            tracks = filteredTracks,
            isLoading = false,
            isSearchActive = isSearchActive,
            currentTrack = playbackState.currentTrack,
            isPlaying = playbackState.isPlaying
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LibraryUiState(isLoading = true)
    )

    init {
        loadTracks()
    }

    private fun loadTracks() {
        viewModelScope.launch {
            getAllTracksUseCase().collect { tracks ->
                _tracks.value = tracks
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSearchOpen() {
        _isSearchActive.value = true
    }

    fun onSearchClose() {
        _isSearchActive.value = false
        _searchQuery.value = ""
    }

    fun importTracks(uris: List<Uri>) {
        viewModelScope.launch {
            uris.forEach { uri ->
                addTrackUseCase(uri)
            }
        }
    }

    fun playTrack(track: Track) {
        viewModelScope.launch {
            // Set queue starting from selected track
            val currentTracks = _tracks.value
            val startIndex = currentTracks.indexOfFirst { it.id == track.id }
                .coerceAtLeast(0)
            playbackController.setQueue(currentTracks, startIndex)
        }
    }

    fun togglePlayPause() {
        viewModelScope.launch {
            playbackController.togglePlayPause()
        }
    }
}
