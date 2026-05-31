package com.musicplayer.core.data.di

import android.content.Context
import androidx.room.Room
import com.musicplayer.core.data.dao.PlaylistDao
import com.musicplayer.core.data.dao.QueueDao
import com.musicplayer.core.data.dao.RecentTrackDao
import com.musicplayer.core.data.dao.TrackDao
import com.musicplayer.core.data.database.MusicDatabase
import com.musicplayer.core.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for data layer dependencies.
 * Provides database, DAOs, and repository implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleBinds {

    @Binds
    @Singleton
    abstract fun bindTrackRepository(
        impl: TrackRepositoryImpl
    ): TrackRepository

    @Binds
    @Singleton
    abstract fun bindPlaylistRepository(
        impl: PlaylistRepositoryImpl
    ): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindQueueRepository(
        impl: QueueRepositoryImpl
    ): QueueRepository

    @Binds
    @Singleton
    abstract fun bindRecentTrackRepository(
        impl: RecentTrackRepositoryImpl
    ): RecentTrackRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModuleProviders {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MusicDatabase {
        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            MusicDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTrackDao(database: MusicDatabase): TrackDao = database.trackDao()

    @Provides
    fun providePlaylistDao(database: MusicDatabase): PlaylistDao = database.playlistDao()

    @Provides
    fun provideQueueDao(database: MusicDatabase): QueueDao = database.queueDao()

    @Provides
    fun provideRecentTrackDao(database: MusicDatabase): RecentTrackDao = database.recentTrackDao()
}
