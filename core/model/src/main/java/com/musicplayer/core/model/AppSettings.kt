package com.musicplayer.core.model

/**
 * User-configurable application settings.
 * Persisted via DataStore.
 */
data class AppSettings(
    /** URI of the custom background image (null = default) */
    val backgroundUri: String? = null,
    /** Background blur radius (0f = no blur, 25f = max) */
    val backgroundBlur: Float = 0f,
    /** Background dim amount (0f = no dim, 0.8f = max) */
    val backgroundDim: Float = 0f,
    /** Background brightness multiplier (0.5f = darker, 1.5f = brighter) */
    val backgroundBrightness: Float = 1.0f,
    /** Whether to resume playback on app launch */
    val autoResumePlayback: Boolean = false,
    /** Whether to stop playback when audio focus is permanently lost */
    val stopOnFocusLoss: Boolean = true,
    /** Whether to pause during transient audio focus loss (calls, notifications) */
    val pauseOnTransientFocusLoss: Boolean = true,
    /** Whether to duck (lower volume) instead of pausing for transient focus loss */
    val duckOnTransientFocusLoss: Boolean = true,
    /** Show notification artwork */
    val showNotificationArtwork: Boolean = true,
    /** Keep screen on during playback */
    val keepScreenOn: Boolean = false
)
