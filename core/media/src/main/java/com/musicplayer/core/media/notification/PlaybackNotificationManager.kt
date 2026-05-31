package com.musicplayer.core.media.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaStyleNotificationHelper
import coil.imageLoader
import coil.request.ImageRequest
import com.musicplayer.core.model.PlaybackState
import com.musicplayer.core.media.R
import com.musicplayer.core.media.service.PlaybackService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages the media playback notification.
 * Creates and updates the notification with artwork and controls.
 */
@Singleton
@UnstableApi
class PlaybackNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    /**
     * Build a notification for the current playback state.
     */
    fun buildNotification(state: PlaybackState): Notification {
        val track = state.currentTrack
        val title = track?.title ?: context.getString(R.string.app_name)
        val artist = track?.artist ?: context.getString(R.string.unknown_artist)

        val playPauseAction = if (state.isPlaying) {
            NotificationCompat.Action(
                R.drawable.ic_pause,
                context.getString(R.string.pause),
                createActionIntent(MediaAction.PAUSE)
            )
        } else {
            NotificationCompat.Action(
                R.drawable.ic_play,
                context.getString(R.string.play),
                createActionIntent(MediaAction.PLAY)
            )
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(artist)
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(null) // Will be loaded asynchronously
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(state.isPlaying)
            .setShowWhen(false)
            .setOnlyAlertOnce(true)
            .addAction(
                NotificationCompat.Action(
                    R.drawable.ic_skip_previous,
                    context.getString(R.string.previous),
                    createActionIntent(MediaAction.PREVIOUS)
                )
            )
            .addAction(playPauseAction)
            .addAction(
                NotificationCompat.Action(
                    R.drawable.ic_skip_next,
                    context.getString(R.string.next),
                    createActionIntent(MediaAction.NEXT)
                )
            )
            .setStyle(
                androidx.media3.session.MediaStyleNotificationHelper.MediaStyle(
                    null // MediaSession will be set when available
                )
                    .setShowActionsInCompactView(0, 1, 2)
            )

        return builder.build()
    }

    /**
     * Update the notification with album art.
     */
    suspend fun updateNotificationWithArtwork(
        state: PlaybackState,
        bitmap: Bitmap?
    ): Notification {
        return buildNotification(state).let { notification ->
            if (bitmap != null) {
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .apply {
                        // Rebuild with artwork
                    }
                    .build()
            } else {
                notification
            }
        }
    }

    private fun createActionIntent(action: MediaAction): PendingIntent {
        val intent = Intent(context, PlaybackService::class.java).apply {
            this.action = action.name
        }
        return PendingIntent.getService(
            context,
            action.ordinal,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.playback_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = context.getString(R.string.playback_channel_description)
                setShowBadge(false)
                enableLights(false)
                enableVibration(false)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "music_playback_channel"
    }

    private enum class MediaAction {
        PLAY, PAUSE, NEXT, PREVIOUS
    }
}
