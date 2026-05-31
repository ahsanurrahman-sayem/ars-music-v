package com.musicplayer.core.data.repository

import com.musicplayer.core.model.AppSettings
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for application settings.
 * Settings are persisted via DataStore.
 */
interface SettingsRepository {

    /** Get current settings as a Flow */
    fun getSettings(): Flow<AppSettings>

    /** Get current settings synchronously */
    suspend fun getSettingsSnapshot(): AppSettings

    /** Update the entire settings object */
    suspend fun updateSettings(settings: AppSettings)

    /** Update background image URI */
    suspend fun setBackgroundUri(uri: String?)

    /** Update background blur amount (0f to 25f) */
    suspend fun setBackgroundBlur(blur: Float)

    /** Update background dim amount (0f to 0.8f) */
    suspend fun setBackgroundDim(dim: Float)

    /** Update background brightness (0.5f to 1.5f) */
    suspend fun setBackgroundBrightness(brightness: Float)

    /** Update auto-resume playback setting */
    suspend fun setAutoResumePlayback(enabled: Boolean)

    /** Update focus loss behavior */
    suspend fun setStopOnFocusLoss(enabled: Boolean)

    /** Update transient focus loss behavior */
    suspend fun setPauseOnTransientFocusLoss(enabled: Boolean)

    /** Update duck on transient focus loss */
    suspend fun setDuckOnTransientFocusLoss(enabled: Boolean)

    /** Update notification artwork display */
    suspend fun setShowNotificationArtwork(enabled: Boolean)

    /** Update keep screen on setting */
    suspend fun setKeepScreenOn(enabled: Boolean)
}
