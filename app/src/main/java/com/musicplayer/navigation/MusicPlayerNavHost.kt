package com.musicplayer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.musicplayer.feature.favorites.FavoritesScreen
import com.musicplayer.feature.library.LibraryScreen
import com.musicplayer.feature.player.PlayerScreen
import com.musicplayer.feature.playlist.PlaylistScreen
import com.musicplayer.feature.queue.QueueScreen
import com.musicplayer.feature.recent.RecentScreen
import com.musicplayer.feature.settings.SettingsScreen

/**
 * Navigation host for the Music Player app.
 * Defines all navigation routes and their associated screens.
 */
@Composable
fun MusicPlayerNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Library.route,
        modifier = modifier
    ) {
        // Library screen (main screen)
        composable(Screen.Library.route) {
            LibraryScreen(
                onTrackClick = { track ->
                    navController.navigate(Screen.Player.route)
                },
                onNavigateToPlayer = {
                    navController.navigate(Screen.Player.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToPlaylists = {
                    navController.navigate(Screen.Playlists.route)
                },
                onNavigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
                },
                onNavigateToRecent = {
                    navController.navigate(Screen.Recent.route)
                }
            )
        }

        // Full-screen player
        composable(Screen.Player.route) {
            PlayerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToQueue = {
                    navController.navigate(Screen.Queue.route)
                }
            )
        }

        // Playlists list
        composable(Screen.Playlists.route) {
            PlaylistScreen(
                onPlaylistClick = { playlistId ->
                    navController.navigate(Screen.PlaylistDetail.createRoute(playlistId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Playlist detail
        composable(
            route = Screen.PlaylistDetail.route,
            arguments = listOf(
                navArgument("playlistId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: return@composable
            // Playlist detail screen would go here
            // For now, navigate back
        }

        // Favorites
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onTrackClick = { track ->
                    navController.navigate(Screen.Player.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Queue
        composable(Screen.Queue.route) {
            QueueScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Recently played
        composable(Screen.Recent.route) {
            RecentScreen(
                onTrackClick = { track ->
                    navController.navigate(Screen.Player.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Settings
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
