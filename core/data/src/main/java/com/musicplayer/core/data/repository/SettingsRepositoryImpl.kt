package com.musicplayer.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.musicplayer.core.model.AppSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

/**
 * Implementation of SettingsRepository using DataStore.
 * All settings are type-safe and persisted automatically.
 */
@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository {

    private val dataStore = context.settingsDataStore

    private object Keys {
        val BACKGROUND_URI = stringPreferencesKey("background_uri")
        val BACKGROUND_BLUR = floatPreferencesKey("background_blur")
        val BACKGROUND_DIM = floatPreferencesKey("background_dim")
        val BACKGROUND_BRIGHTNESS = floatPreferencesKey("background_brightness")
        val AUTO_RESUME_PLAYBACK = booleanPreferencesKey("auto_resume_playback")
        val STOP_ON_FOCUS_LOSS = booleanPreferencesKey("stop_on_focus_loss")
        val PAUSE_ON_TRANSIENT_FOCUS_LOSS = booleanPreferencesKey("pause_on_transient_focus_loss")
        val DUCK_ON_TRANSIENT_FOCUS_LOSS = booleanPreferencesKey("duck_on_transient_focus_loss")
        val SHOW_NOTIFICATION_ARTWORK = booleanPreferencesKey("show_notification_artwork")
        val KEEP_SCREEN_ON = booleanPreferencesKey("keep_screen_on")
    }

    override fun getSettings(): Flow<AppSettings> = dataStore.data.map { prefs ->
        AppSettings(
            backgroundUri = prefs[Keys.BACKGROUND_URI],
            backgroundBlur = prefs[Keys.BACKGROUND_BLUR] ?: 0f,
            backgroundDim = prefs[Keys.BACKGROUND_DIM] ?: 0f,
            backgroundBrightness = prefs[Keys.BACKGROUND_BRIGHTNESS] ?: 1.0f,
            autoResumePlayback = prefs[Keys.AUTO_RESUME_PLAYBACK] ?: false,
            stopOnFocusLoss = prefs[Keys.STOP_ON_FOCUS_LOSS] ?: true,
            pauseOnTransientFocusLoss = prefs[Keys.PAUSE_ON_TRANSIENT_FOCUS_LOSS] ?: true,
            duckOnTransientFocusLoss = prefs[Keys.DUCK_ON_TRANSIENT_FOCUS_LOSS] ?: true,
            showNotificationArtwork = prefs[Keys.SHOW_NOTIFICATION_ARTWORK] ?: true,
            keepScreenOn = prefs[Keys.KEEP_SCREEN_ON] ?: false
        )
    }

    override suspend fun getSettingsSnapshot(): AppSettings = getSettings().first()

    override suspend fun updateSettings(settings: AppSettings) {
        dataStore.edit { prefs ->
            prefs[Keys.BACKGROUND_URI] = settings.backgroundUri
            prefs[Keys.BACKGROUND_BLUR] = settings.backgroundBlur
            prefs[Keys.BACKGROUND_DIM] = settings.backgroundDim
            prefs[Keys.BACKGROUND_BRIGHTNESS] = settings.backgroundBrightness
            prefs[Keys.AUTO_RESUME_PLAYBACK] = settings.autoResumePlayback
            prefs[Keys.STOP_ON_FOCUS_LOSS] = settings.stopOnFocusLoss
            prefs[Keys.PAUSE_ON_TRANSIENT_FOCUS_LOSS] = settings.pauseOnTransientFocusLoss
            prefs[Keys.DUCK_ON_TRANSIENT_FOCUS_LOSS] = settings.duckOnTransientFocusLoss
            prefs[Keys.SHOW_NOTIFICATION_ARTWORK] = settings.showNotificationArtwork
            prefs[Keys.KEEP_SCREEN_ON] = settings.keepScreenOn
        }
    }

    override suspend fun setBackgroundUri(uri: String?) {
        dataStore.edit { prefs ->
            if (uri != null) {
                prefs[Keys.BACKGROUND_URI] = uri
            } else {
                prefs.remove(Keys.BACKGROUND_URI)
            }
        }
    }

    override suspend fun setBackgroundBlur(blur: Float) {
        dataStore.edit { prefs ->
            prefs[Keys.BACKGROUND_BLUR] = blur.coerceIn(0f, 25f)
        }
    }

    override suspend fun setBackgroundDim(dim: Float) {
        dataStore.edit { prefs ->
            prefs[Keys.BACKGROUND_DIM] = dim.coerceIn(0f, 0.8f)
        }
    }

    override suspend fun setBackgroundBrightness(brightness: Float) {
        dataStore.edit { prefs ->
            prefs[Keys.BACKGROUND_BRIGHTNESS] = brightness.coerceIn(0.5f, 1.5f)
        }
    }

    override suspend fun setAutoResumePlayback(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.AUTO_RESUME_PLAYBACK] = enabled
        }
    }

    override suspend fun setStopOnFocusLoss(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.STOP_ON_FOCUS_LOSS] = enabled
        }
    }

    override suspend fun setPauseOnTransientFocusLoss(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.PAUSE_ON_TRANSIENT_FOCUS_LOSS] = enabled
        }
    }

    override suspend fun setDuckOnTransientFocusLoss(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.DUCK_ON_TRANSIENT_FOCUS_LOSS] = enabled
        }
    }

    override suspend fun setShowNotificationArtwork(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.SHOW_NOTIFICATION_ARTWORK] = enabled
        }
    }

    override suspend fun setKeepScreenOn(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.KEEP_SCREEN_ON] = enabled
        }
    }
}
