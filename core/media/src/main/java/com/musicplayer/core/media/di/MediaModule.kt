package com.musicplayer.core.media.di

import android.content.Context
import android.media.AudioManager
import com.musicplayer.core.domain.usecase.playlist.PlaybackController
import com.musicplayer.core.media.focus.AudioFocusManager
import com.musicplayer.core.media.player.MediaPlayerController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for media layer dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MediaModule {

    @Binds
    @Singleton
    abstract fun bindPlaybackController(
        mediaPlayerController: MediaPlayerController
    ): PlaybackController
}

@Module
@InstallIn(SingletonComponent::class)
object MediaProviderModule {

    @Provides
    @Singleton
    fun provideAudioManager(
        @ApplicationContext context: Context
    ): AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    @Provides
    @Singleton
    fun provideAudioFocusManager(
        audioManager: AudioManager
    ): AudioFocusManager = AudioFocusManager(audioManager)
}
