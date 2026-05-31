package com.musicplayer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Music Player app.
 * Initializes Hilt for dependency injection.
 */
@HiltAndroidApp
class MusicPlayerApplication : Application()
