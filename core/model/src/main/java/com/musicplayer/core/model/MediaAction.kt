package com.musicplayer.core.model

/**
 * Sealed class representing all possible media control actions.
 * Used for communication between UI and media service.
 */
sealed class MediaAction {
    data class Play(val track: Track? = null) : MediaAction()
    data object Pause : MediaAction()
    data object TogglePlayPause : MediaAction()
    data object Next : MediaAction()
    data object Previous : MediaAction()
    data class Seek(val positionMs: Long) : MediaAction()
    data class SetRepeatMode(val mode: RepeatMode) : MediaAction()
    data class SetShuffle(val enabled: Boolean) : MediaAction()
    data class SetQueue(val tracks: List<Track>, val startIndex: Int = 0) : MediaAction()
    data class AddToQueue(val track: Track) : MediaAction()
    data class RemoveFromQueue(val index: Int) : MediaAction()
    data class MoveQueueItem(val fromIndex: Int, val toIndex: Int) : MediaAction()
    data class SetSpeed(val speed: Float) : MediaAction()
}
