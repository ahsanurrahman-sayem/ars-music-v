package com.musicplayer.core.domain.usecase.playback

import com.musicplayer.core.model.PlaybackState
import com.musicplayer.core.model.RepeatMode
import com.musicplayer.core.model.Track
import kotlinx.coroutines.flow.Flow

/**
 * Interface for playback control operations.
 * Implemented by the media module to abstract Media3 specifics.
 */
interface PlaybackController {

    /** Current playback state as a Flow */
    val playbackState: Flow<PlaybackState>

    /** Play a specific track */
    suspend fun play(track: Track)

    /** Pause playback */
    suspend fun pause()

    /** Toggle between play and pause */
    suspend fun togglePlayPause()

    /** Skip to next track */
    suspend fun skipToNext()

    /** Skip to previous track */
    suspend fun skipToPrevious()

    /** Seek to a specific position in milliseconds */
    suspend fun seekTo(positionMs: Long)

    /** Set repeat mode */
    suspend fun setRepeatMode(mode: RepeatMode)

    /** Toggle shuffle mode */
    suspend fun setShuffle(enabled: Boolean)

    /** Set the playback queue */
    suspend fun setQueue(tracks: List<Track>, startIndex: Int = 0)

    /** Add a track to the end of the queue */
    suspend fun addToQueue(track: Track)

    /** Remove a track from the queue by index */
    suspend fun removeFromQueue(index: Int)

    /** Move a queue item from one position to another */
    suspend fun moveQueueItem(fromIndex: Int, toIndex: Int)

    /** Set playback speed */
    suspend fun setPlaybackSpeed(speed: Float)

    /** Release resources */
    fun release()
}

/**
 * Use case to observe playback state.
 */
class ObservePlaybackStateUseCase(
    private val playbackController: PlaybackController
) {
    operator fun invoke(): Flow<PlaybackState> = playbackController.playbackState
}

/**
 * Use case to play a track.
 */
class PlayTrackUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke(track: Track) = playbackController.play(track)
}

/**
 * Use case to toggle play/pause.
 */
class TogglePlaybackUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke() = playbackController.togglePlayPause()
}

/**
 * Use case to skip to next track.
 */
class SkipToNextUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke() = playbackController.skipToNext()
}

/**
 * Use case to skip to previous track.
 */
class SkipToPreviousUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke() = playbackController.skipToPrevious()
}

/**
 * Use case to seek to a position.
 */
class SeekToUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke(positionMs: Long) = playbackController.seekTo(positionMs)
}

/**
 * Use case to set repeat mode.
 */
class SetRepeatModeUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke(mode: RepeatMode) = playbackController.setRepeatMode(mode)
}

/**
 * Use case to set shuffle mode.
 */
class SetShuffleUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke(enabled: Boolean) = playbackController.setShuffle(enabled)
}

/**
 * Use case to set the playback queue.
 */
class SetQueueUseCase(
    private val playbackController: PlaybackController
) {
    suspend operator fun invoke(tracks: List<Track>, startIndex: Int = 0) =
        playbackController.setQueue(tracks, startIndex)
}
