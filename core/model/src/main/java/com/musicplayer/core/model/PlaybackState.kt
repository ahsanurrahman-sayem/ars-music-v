package com.musicplayer.core.model

/**
 * Represents the current playback state of the media player.
 * Immutable data class for state flow emissions.
 */
data class PlaybackState(
    /** Currently playing track, or null if nothing is playing */
    val currentTrack: Track? = null,
    /** Whether audio is currently playing */
    val isPlaying: Boolean = false,
    /** Current playback position in milliseconds */
    val positionMs: Long = 0,
    /** Total duration of current track in milliseconds */
    val durationMs: Long = 0,
    /** Playback speed (1.0 = normal) */
    val playbackSpeed: Float = 1.0f,
    /** Current repeat mode */
    val repeatMode: RepeatMode = RepeatMode.OFF,
    /** Whether shuffle is enabled */
    val isShuffleOn: Boolean = false,
    /** Available commands that can be executed */
    val availableCommands: PlayerCommands = PlayerCommands(),
    /** Audio session ID for visualizer/equalizer integration */
    val audioSessionId: Int = 0
)

/**
 * Repeat mode options for playback.
 */
enum class RepeatMode {
    OFF,        // No repeat, stop at end
    ONE,        // Repeat current track
    ALL         // Repeat entire queue
}

/**
 * Available player commands based on current state.
 */
data class PlayerCommands(
    val canPlay: Boolean = false,
    val canPause: Boolean = false,
    val canSkipNext: Boolean = false,
    val canSkipPrevious: Boolean = false,
    val canSeek: Boolean = false
)
