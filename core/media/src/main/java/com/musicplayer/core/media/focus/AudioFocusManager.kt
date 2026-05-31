package com.musicplayer.core.media.focus

import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import com.musicplayer.core.model.AudioFocusState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages audio focus for the music player.
 * Ensures proper behavior when other apps request audio focus (calls, notifications, etc.)
 * 
 * Features:
 * - Graceful ducking (lowering volume) on transient focus loss
 * - Pause on audio focus loss from other media apps
 * - Resume when focus is regained
 * - Configurable behavior via settings
 */
@Singleton
class AudioFocusManager @Inject constructor(
    private val audioManager: AudioManager
) {

    private val _focusState = MutableStateFlow(AudioFocusState.GAINED)
    val focusState: StateFlow<AudioFocusState> = _focusState.asStateFlow()

    private var focusRequest: AudioFocusRequest? = null

    private val focusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        val newState = when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> AudioFocusState.GAINED
            AudioManager.AUDIOFOCUS_LOSS -> AudioFocusState.LOST
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> AudioFocusState.LOST_TRANSIENT
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> AudioFocusState.LOST_TRANSIENT_CAN_DUCK
            else -> AudioFocusState.GAINED
        }
        _focusState.value = newState
    }

    /**
     * Request audio focus for music playback.
     * Returns true if focus was granted.
     */
    fun requestFocus(): Boolean {
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setOnAudioFocusChangeListener(focusChangeListener, Handler(Looper.getMainLooper()))
                .setWillPauseWhenDucked(true)
                .build()
            focusRequest = request
            audioManager.requestAudioFocus(request)
        } else {
            @Suppress("DEPRECATION")
            audioManager.requestAudioFocus(
                focusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }

        val granted = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        if (granted) {
            _focusState.value = AudioFocusState.GAINED
        }
        return granted
    }

    /**
     * Abandon audio focus when playback stops.
     */
    fun abandonFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusRequest?.let { request ->
                audioManager.abandonAudioFocusRequest(request)
            }
        } else {
            @Suppress("DEPRECATION")
            audioManager.abandonAudioFocus(focusChangeListener)
        }
        focusRequest = null
    }
}
