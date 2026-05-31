package com.musicplayer.core.model

/**
 * Represents audio focus states for proper audio focus handling.
 * Ensures the player behaves correctly with other audio apps.
 */
enum class AudioFocusState {
    /** Full audio focus granted - normal playback */
    GAINED,
    /** Audio focus lost long-term - stop playback */
    LOST,
    /** Audio focus lost short-term - pause playback */
    LOST_TRANSIENT,
    /** Audio focus lost short-term but can duck - lower volume */
    LOST_TRANSIENT_CAN_DUCK
}
