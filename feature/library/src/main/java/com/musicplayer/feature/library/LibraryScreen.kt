package com.musicplayer.feature.library

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicplayer.core.common.TimeUtils
import com.musicplayer.core.model.Track
import com.musicplayer.core.ui.component.TrackListItem
import com.musicplayer.core.ui.theme.MusicPlayerTheme

/**
 * Main library screen showing all imported tracks.
 * Provides track list, search, and import functionality.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onTrackClick: (Track) -> Unit,
    onNavigateToPlayer: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPlaylists: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToRecent: () -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // Document picker for importing tracks
    val documentPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.importTracks(uris)
        }
    }

    // Receive shared audio files from other apps
    val shareReceiver = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) {
            viewModel.importTracks(uris)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (uiState.isSearchActive) {
                        TextField(
                            value = searchQuery,
                            onValueChange = viewModel::onSearchQueryChange,
                            placeholder = { Text("Search tracks...") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text("Library")
                    }
                },
                navigationIcon = {
                    if (uiState.isSearchActive) {
                        IconButton(onClick = viewModel::onSearchClose) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Close search")
                        }
                    }
                },
                actions = {
                    if (!uiState.isSearchActive) {
                        IconButton(onClick = viewModel::onSearchOpen) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    documentPicker.launch(arrayOf("audio/*"))
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Import tracks")
            }
        },
        bottomBar = {
            // Mini player at bottom
            if (uiState.currentTrack != null) {
                MiniPlayer(
                    track = uiState.currentTrack,
                    isPlaying = uiState.isPlaying,
                    onPlayPauseClick = viewModel::togglePlayPause,
                    onTrackClick = {
	//val currentTrack = uiState.currentTrack
	if (uiState.currentTrack != null) {
		onTrackClick(uiState.rurrentTrack)
	}
},
                    modifier = Modifier.clickable { onNavigateToPlayer() }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.tracks.isEmpty() -> {
                    EmptyLibraryContent(
                        onImportClick = {
                            documentPicker.launch(arrayOf("audio/*"))
                        },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    TrackList(
                        tracks = uiState.tracks,
                        currentTrackId = uiState.currentTrack?.id,
                        isPlaying = uiState.isPlaying,
                        onTrackClick = { track ->
                            viewModel.playTrack(track)
                            onTrackClick(track)
                        },
                        onTrackOptionsClick = { track ->
                            // Show track options bottom sheet
                        }
                    )
                }
            }
        }
    }
}

/**
 * List of tracks with current playing indicator.
 */
@Composable
private fun TrackList(
    tracks: List<Track>,
    currentTrackId: String?,
    isPlaying: Boolean,
    onTrackClick: (Track) -> Unit,
    onTrackOptionsClick: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = tracks,
            key = { it.id }
        ) { track ->
            TrackListItem(
                track = track,
                onClick = { onTrackClick(track) },
                isPlaying = track.id == currentTrackId && isPlaying,
                trailingContent = {
                    IconButton(
                        onClick = { onTrackOptionsClick(track) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Track options",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            )
        }
    }
}

/**
 * Empty state shown when no tracks are imported.
 */
@Composable
private fun EmptyLibraryContent(
    onImportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.LibraryMusic,
            contentDescription = null,
            modifier = Modifier.size(96.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your library is empty",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Import audio files to get started. We never scan your device.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onImportClick) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Import Tracks")
        }
    }
}

/**
 * Mini player shown at bottom of screen.
 */
@Composable
private fun MiniPlayer(
    track: Track,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onTrackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Artwork
            com.musicplayer.core.ui.component.TrackArtwork(
                track = track,
                modifier = Modifier.size(48.dp),
                isPlaying = isPlaying
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Track info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onTrackClick)
            ) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = track.artist,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Play/Pause
            IconButton(onClick = onPlayPauseClick) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }
        }
    }
}
