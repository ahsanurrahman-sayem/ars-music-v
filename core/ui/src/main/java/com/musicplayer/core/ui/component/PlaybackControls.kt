package com.musicplayer.core.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.musicplayer.core.model.RepeatMode
import com.musicplayer.core.common.TimeUtils

@Composable
fun MainPlaybackControls(
    isPlaying: Boolean,
    canSkipPrevious: Boolean,
    canSkipNext: Boolean,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPreviousClick,
            enabled = canSkipPrevious
        ) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = "Previous",
                modifier = Modifier.size(32.dp)
            )
        }

        FilledIconButton(
            onClick = onPlayPauseClick,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                modifier = Modifier.size(32.dp)
            )
        }

        IconButton(
            onClick = onNextClick,
            enabled = canSkipNext
        ) {
            Icon(
                imageVector = Icons.Default.SkipNext,
                contentDescription = "Next",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun SecondaryPlaybackControls(
    isShuffleOn: Boolean,
    repeatMode: RepeatMode,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onShuffleClick) {
            Icon(
                imageVector = Icons.Default.Shuffle,
                contentDescription = "Shuffle",
                tint = if (isShuffleOn) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }

        IconButton(onClick = onRepeatClick) {
            when (repeatMode) {
                RepeatMode.OFF -> {
                    Icon(
                        imageVector = Icons.Default.Repeat,
                        contentDescription = "Repeat off",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                RepeatMode.ONE -> {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.primary
                            ) {
                                Text("1")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Repeat,
                            contentDescription = "Repeat one",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                RepeatMode.ALL -> {
                    Icon(
                        imageVector = Icons.Default.Repeat,
                        contentDescription = "Repeat all",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SeekBar(
    positionMs: Long,
    durationMs: Long,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val position = (positionMs / 1000f).coerceAtLeast(0f)
    val duration = (durationMs / 1000f).coerceAtLeast(1f)
    val progress = (position / duration).coerceIn(0f, 1f)

    Column(modifier = modifier.fillMaxWidth()) {
        Slider(
            value = progress,
            onValueChange = { newProgress ->
                onSeek((newProgress * durationMs).toLong())
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = TimeUtils.formatDuration(positionMs),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = TimeUtils.formatDuration(durationMs),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
