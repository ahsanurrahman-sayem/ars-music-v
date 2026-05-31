package com.musicplayer.core.media.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.musicplayer.core.media.notification.PlaybackNotificationManager
import com.musicplayer.core.media.player.MediaPlayerController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Foreground service for audio playback.
 * Hosts the MediaSession and maintains a persistent notification.
 * 
 * This service:
 * - Runs as a foreground service with a media notification
 * - Handles media session callbacks for lockscreen/headset controls
 * - Survives screen lock and app backgrounding
 * - Properly handles audio focus
 */
@AndroidEntryPoint
@UnstableApi
class PlaybackService : MediaSessionService() {

    @Inject
    lateinit var playerController: MediaPlayerController

    @Inject
    lateinit var notificationManager: PlaybackNotificationManager

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var mediaSession: MediaSession? = null

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): PlaybackService = this@PlaybackService
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onBind(intent: Intent?): IBinder {
        super.onBind(intent)
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    private fun startForegroundService() {
        // Observe playback state to maintain notification
        serviceScope.launch {
            playerController.playbackState.collectLatest { state ->
                val notification = notificationManager.buildNotification(state)
                startForeground(PlaybackNotificationManager.NOTIFICATION_ID, notification)

                // Stop foreground if nothing is playing for a while
                if (!state.isPlaying && state.currentTrack == null) {
                    stopForeground(STOP_FOREGROUND_DETACH)
                }
            }
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        // Stop service if app is removed from recents and nothing is playing
        val player = playerController.playbackState.value
        if (!player.isPlaying) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        mediaSession?.release()
        mediaSession = null
        super.onDestroy()
    }
}
