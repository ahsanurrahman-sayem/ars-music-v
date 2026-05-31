package com.musicplayer.core.media.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.RepeatModeOptions.RepeatMode
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.musicplayer.core.common.DispatcherProvider
import com.musicplayer.core.domain.usecase.playlist.PlaybackController
import com.musicplayer.core.model.PlaybackState as AppPlaybackState
import com.musicplayer.core.model.PlayerCommands
import com.musicplayer.core.model.RepeatMode as AppRepeatMode
import com.musicplayer.core.model.Track
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Media3-based implementation of the PlaybackController interface.
 * Manages ExoPlayer instance and exposes playback state via StateFlow.
 */
@Singleton
@UnstableApi
class MediaPlayerController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : PlaybackController {

    private val scope = CoroutineScope(dispatcherProvider.main + SupervisorJob())

    private val _playbackState = MutableStateFlow(AppPlaybackState())
    override val playbackState: StateFlow<AppPlaybackState> = _playbackState.asStateFlow()

    private var exoPlayer: ExoPlayer? = null
    private var positionUpdateJob: kotlinx.coroutines.Job? = null

    private val playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _playbackState.update { it.copy(isPlaying = isPlaying) }
            if (isPlaying) startPositionUpdates() else stopPositionUpdates()
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            updateCommands()
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            mediaItem?.let { item ->
                val track = item.localConfiguration?.tag as? Track
                track?.let { t ->
                    _playbackState.update {
                        it.copy(
                            currentTrack = t,
                            durationMs = exoPlayer?.duration?.coerceAtLeast(0) ?: 0
                        )
                    }
                }
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            // Handle error - could emit an error event
            _playbackState.update { it.copy(isPlaying = false) }
        }

        override fun onRepeatModeChanged(@RepeatMode repeatMode: Int) {
            val mode = when (repeatMode) {
                Player.REPEAT_MODE_OFF -> AppRepeatMode.OFF
                Player.REPEAT_MODE_ONE -> AppRepeatMode.ONE
                Player.REPEAT_MODE_ALL -> AppRepeatMode.ALL
                else -> AppRepeatMode.OFF
            }
            _playbackState.update { it.copy(repeatMode = mode) }
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            _playbackState.update { it.copy(isShuffleOn = shuffleModeEnabled) }
        }
    }

    init {
        initializePlayer()
    }

    private fun initializePlayer() {
        val player = ExoPlayer.Builder(context)
            .setAudioAttributes(
                androidx.media3.common.AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .build(),
                true // handleAudioFocus
            )
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build()
            .apply {
                addListener(playerListener)
            }

        exoPlayer = player
        updateCommands()
    }

    override suspend fun play(track: Track) {
        val player = exoPlayer ?: return
        val mediaItem = createMediaItem(track)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        _playbackState.update {
            it.copy(
                currentTrack = track,
                isPlaying = true
            )
        }
    }

    override suspend fun pause() {
        exoPlayer?.pause()
    }

    override suspend fun togglePlayPause() {
        val player = exoPlayer ?: return
        if (player.isPlaying) {
            player.pause()
        } else {
            if (player.playbackState == Player.STATE_IDLE) {
                player.prepare()
            }
            player.play()
        }
    }

    override suspend fun skipToNext() {
        exoPlayer?.seekToNext()
    }

    override suspend fun skipToPrevious() {
        exoPlayer?.seekToPrevious()
    }

    override suspend fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
        _playbackState.update { it.copy(positionMs = positionMs) }
    }

    override suspend fun setRepeatMode(mode: AppRepeatMode) {
        val exoMode = when (mode) {
            AppRepeatMode.OFF -> Player.REPEAT_MODE_OFF
            AppRepeatMode.ONE -> Player.REPEAT_MODE_ONE
            AppRepeatMode.ALL -> Player.REPEAT_MODE_ALL
        }
        exoPlayer?.repeatMode = exoMode
    }

    override suspend fun setShuffle(enabled: Boolean) {
        exoPlayer?.shuffleModeEnabled = enabled
    }

    override suspend fun setQueue(tracks: List<Track>, startIndex: Int) {
        val player = exoPlayer ?: return
        val mediaItems = tracks.map { createMediaItem(it) }
        player.setMediaItems(mediaItems, startIndex.coerceIn(0, tracks.size - 1), 0)
        player.prepare()
        player.play()

        if (tracks.isNotEmpty()) {
            val startTrack = tracks[startIndex.coerceIn(0, tracks.lastIndex)]
            _playbackState.update {
                it.copy(
                    currentTrack = startTrack,
                    isPlaying = true
                )
            }
        }
    }

    override suspend fun addToQueue(track: Track) {
        val mediaItem = createMediaItem(track)
        exoPlayer?.addMediaItem(mediaItem)
    }

    override suspend fun removeFromQueue(index: Int) {
        exoPlayer?.removeMediaItem(index)
    }

    override suspend fun moveQueueItem(fromIndex: Int, toIndex: Int) {
        exoPlayer?.moveMediaItem(fromIndex, toIndex)
    }

    override suspend fun setPlaybackSpeed(speed: Float) {
        exoPlayer?.setPlaybackSpeed(speed.coerceIn(0.25f, 2.0f))
        _playbackState.update { it.copy(playbackSpeed = speed) }
    }

    override fun release() {
        stopPositionUpdates()
        scope.cancel()
        exoPlayer?.removeListener(playerListener)
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun createMediaItem(track: Track): MediaItem {
        return MediaItem.Builder()
            .setUri(Uri.parse(track.uri))
            .setMediaId(track.id)
            .setTag(track)
            .build()
    }

    private fun startPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = scope.launch {
            while (isActive) {
                val player = exoPlayer ?: break
                if (player.isPlaying) {
                    _playbackState.update {
                        it.copy(
                            positionMs = player.currentPosition.coerceAtLeast(0),
                            durationMs = player.duration.coerceAtLeast(0)
                        )
                    }
                }
                delay(200) // Update 5 times per second for smooth UI
            }
        }
    }

    private fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }

    private fun updateCommands() {
        val player = exoPlayer ?: return
        val hasMediaItems = player.mediaItemCount > 0

        _playbackState.update {
            it.copy(
                availableCommands = PlayerCommands(
                    canPlay = hasMediaItems && !player.isPlaying,
                    canPause = player.isPlaying,
                    canSkipNext = hasMediaItems && player.hasNextMediaItem(),
                    canSkipPrevious = hasMediaItems && player.hasPreviousMediaItem(),
                    canSeek = hasMediaItems
                )
            )
        }
    }

    /** Get the audio session ID for visualizer integration */
    fun getAudioSessionId(): Int = exoPlayer?.audioSessionId ?: 0
}
