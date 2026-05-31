package com.musicplayer.feature.player

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.musicplayer.core.common.TimeUtils
import com.musicplayer.core.model.PlaybackState
import com.musicplayer.core.model.RepeatMode
import com.musicplayer.core.model.Track
import com.musicplayer.core.ui.component.*
import com.musicplayer.core.ui.theme.MusicPlayerTheme

/**
 * Full-screen player screen with artwork, controls, and seek bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    onNavigateBack: () -> Unit,
    onNavigateToQueue: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val playbackState by viewModel.playbackState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Now Playing") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ExpandMore, contentDescription = "Collapse")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToQueue) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.QueueMusic,
                            contentDescription = "Queue"
                        )
                    }
                    IconButton(onClick = { /* More options */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Options")
                    }
                }
            )
        }
    ) { paddingValues ->
        PlayerContent(
            playbackState = playbackState,
            onPlayPauseClick = viewModel::togglePlayPause,
            onNextClick = viewModel::skipToNext,
            onPreviousClick = viewModel::skipToPrevious,
            onSeek = viewModel::seekTo,
            onShuffleClick = viewModel::toggleShuffle,
            onRepeatClick = viewModel::cycleRepeatMode,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * Main player content with artwork and controls.
 */
@Composable
private fun PlayerContent(
    playbackState: PlaybackState,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onSeek: (Long) -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val track = playbackState.currentTrack

    if (track == null) {
        EmptyPlayer(modifier = modifier)
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        // Artwork
        PlayerArtwork(
            track = track,
            isPlaying = playbackState.isPlaying,
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Track info
        Text(
            text = track.title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = track.artist,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Seek bar
        SeekBar(
            positionMs = playbackState.positionMs,
            durationMs = playbackState.durationMs,
            onSeek = onSeek,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Main controls
        MainPlaybackControls(
            isPlaying = playbackState.isPlaying,
            canSkipPrevious = playbackState.availableCommands.canSkipPrevious,
            canSkipNext = playbackState.availableCommands.canSkipNext,
            onPreviousClick = onPreviousClick,
            onPlayPauseClick = onPlayPauseClick,
            onNextClick = onNextClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Secondary controls
        SecondaryPlaybackControls(
            isShuffleOn = playbackState.isShuffleOn,
            repeatMode = playbackState.repeatMode,
            onShuffleClick = onShuffleClick,
            onRepeatClick = onRepeatClick
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

/**
 * Large artwork display for the player.
 */
@Composable
private fun PlayerArtwork(
    track: Track,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .background(
                if (isPlaying) {
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (track.customCoverUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(track.customCoverUri)
                    .crossfade(true)
                    .build(),
                contentDescription = track.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

/**
 * Empty state when no track is selected.
 */
@Composable
private fun EmptyPlayer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No track playing",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}
