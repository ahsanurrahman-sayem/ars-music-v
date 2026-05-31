package com.musicplayer.navigation

/**
 * Sealed class defining all screens in the app.
 * Used for type-safe navigation.
 */
sealed class Screen(val route: String) {
    data object Library : Screen("library")
    data object Player : Screen("player")
    data object Playlists : Screen("playlists")
    data object PlaylistDetail : Screen("playlist/{playlistId}") {
        fun createRoute(playlistId: String) = "playlist/$playlistId"
    }
    data object Favorites : Screen("favorites")
    data object Queue : Screen("queue")
    data object Recent : Screen("recent")
    data object Settings : Screen("settings")
}
