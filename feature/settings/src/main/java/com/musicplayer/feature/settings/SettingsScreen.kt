package com.musicplayer.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musicplayer.core.model.AppSettings

/**
 * Settings screen for user-configurable preferences.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Appearance Section
            SettingsSectionTitle("Appearance")

            // Background settings
            ListItem(
                headlineContent = { Text("Background Image") },
                supportingContent = {
                    Text(
                        if (settings.backgroundUri != null)
                            "Custom background set"
                        else
                            "Tap to choose a background image"
                    )
                },
                leadingContent = {
                    Icon(Icons.Default.Image, contentDescription = null)
                },
                modifier = Modifier.clickable { viewModel.pickBackgroundImage() }
            )

            if (settings.backgroundUri != null) {
                // Blur slider
                SettingsSlider(
                    title = "Blur",
                    value = settings.backgroundBlur,
                    onValueChange = viewModel::setBackgroundBlur,
                    valueRange = 0f..25f
                )

                // Dim slider
                SettingsSlider(
                    title = "Dim",
                    value = settings.backgroundDim,
                    onValueChange = viewModel::setBackgroundDim,
                    valueRange = 0f..0.8f
                )

                // Brightness slider
                SettingsSlider(
                    title = "Brightness",
                    value = settings.backgroundBrightness,
                    onValueChange = viewModel::setBackgroundBrightness,
                    valueRange = 0.5f..1.5f
                )

                // Remove background
                ListItem(
                    headlineContent = { Text("Remove Background") },
                    leadingContent = {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    },
                    modifier = Modifier.clickable { viewModel.removeBackground() }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Playback Section
            SettingsSectionTitle("Playback")

            SettingsSwitch(
                title = "Auto-resume Playback",
                description = "Resume playing when app opens",
                checked = settings.autoResumePlayback,
                onCheckedChange = viewModel::setAutoResumePlayback
            )

            SettingsSwitch(
                title = "Keep Screen On",
                description = "Prevent screen from sleeping during playback",
                checked = settings.keepScreenOn,
                onCheckedChange = viewModel::setKeepScreenOn
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Audio Focus Section
            SettingsSectionTitle("Audio Focus")

            SettingsSwitch(
                title = "Stop on Focus Loss",
                description = "Stop playback when another app takes audio focus",
                checked = settings.stopOnFocusLoss,
                onCheckedChange = viewModel::setStopOnFocusLoss
            )

            SettingsSwitch(
                title = "Pause on Transient Loss",
                description = "Pause during calls and notifications",
                checked = settings.pauseOnTransientFocusLoss,
                onCheckedChange = viewModel::setPauseOnTransientFocusLoss
            )

            SettingsSwitch(
                title = "Duck Volume",
                description = "Lower volume instead of pausing for notifications",
                checked = settings.duckOnTransientFocusLoss,
                onCheckedChange = viewModel::setDuckOnTransientFocusLoss
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Notification Section
            SettingsSectionTitle("Notification")

            SettingsSwitch(
                title = "Show Artwork",
                description = "Display track artwork in notification",
                checked = settings.showNotificationArtwork,
                onCheckedChange = viewModel::setShowNotificationArtwork
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun SettingsSwitch(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(description) },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    )
}

@Composable
private fun SettingsSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$title: ${String.format("%.1f", value)}",
            style = MaterialTheme.typography.bodyMedium
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
