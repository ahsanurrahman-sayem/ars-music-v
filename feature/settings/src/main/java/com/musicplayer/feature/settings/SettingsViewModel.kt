package com.musicplayer.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.core.data.repository.SettingsRepository
import com.musicplayer.core.model.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = settingsRepository.getSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppSettings()
        )

    fun setBackgroundBlur(blur: Float) {
        viewModelScope.launch { settingsRepository.setBackgroundBlur(blur) }
    }

    fun setBackgroundDim(dim: Float) {
        viewModelScope.launch { settingsRepository.setBackgroundDim(dim) }
    }

    fun setBackgroundBrightness(brightness: Float) {
        viewModelScope.launch { settingsRepository.setBackgroundBrightness(brightness) }
    }

    fun setAutoResumePlayback(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setAutoResumePlayback(enabled) }
    }

    fun setStopOnFocusLoss(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setStopOnFocusLoss(enabled) }
    }

    fun setPauseOnTransientFocusLoss(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setPauseOnTransientFocusLoss(enabled) }
    }

    fun setDuckOnTransientFocusLoss(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setDuckOnTransientFocusLoss(enabled) }
    }

    fun setShowNotificationArtwork(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setShowNotificationArtwork(enabled) }
    }

    fun setKeepScreenOn(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setKeepScreenOn(enabled) }
    }

    fun pickBackgroundImage() {
        // This would trigger a photo picker from the UI
        // The actual URI would be passed to setBackgroundUri
    }

    fun removeBackground() {
        viewModelScope.launch { settingsRepository.setBackgroundUri(null) }
    }
}
