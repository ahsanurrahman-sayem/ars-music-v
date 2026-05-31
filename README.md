# Music Player

A lightweight, privacy-focused Android music player built with modern Android best practices.

## Features

- **Manual Track Import**: Select audio files via Android document picker - no storage scanning
- **Zero File Duplication**: Only stores URI references, never copies audio files
- **Background Playback**: Persistent foreground service with media notification
- **Queue Management**: Dynamic playback queue with reorder support
- **Playlists**: Create and manage custom playlists
- **Favorites**: Mark tracks as favorites for quick access
- **Recently Played**: Track playback history with timestamps
- **Custom Backgrounds**: Personalize the app with custom backgrounds (blur, dim, brightness)
- **Custom Cover Art**: Assign custom images to any track
- **Audio Focus**: Proper handling of audio focus changes (calls, notifications)
- **Bluetooth Support**: Media controls work with Bluetooth headsets
- **Lockscreen Controls**: Playback controls on lockscreen

## Architecture

The app follows **Clean Architecture** with a modular project structure:

```
MusicPlayer/
├── app/                    # Main application module
├── core/                   # Core modules
│   ├── model/             # Data models and entities
│   ├── common/            # Shared utilities and extensions
│   ├── data/              # Data layer (Room DB, repositories)
│   ├── domain/            # Domain layer (use cases)
│   ├── media/             # Media playback (Media3, service)
│   └── ui/                # Shared UI components and theme
└── feature/               # Feature modules
    ├── library/           # Music library screen
    ├── player/            # Full-screen player
    ├── playlist/          # Playlist management
    ├── favorites/         # Favorites screen
    ├── queue/             # Queue screen
    ├── recent/            # Recently played screen
    └── settings/          # App settings
```

## Technical Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | Clean Architecture + MVVM |
| DI | Hilt |
| Media | Media3 (ExoPlayer) |
| Database | Room |
| Preferences | DataStore |
| Images | Coil |
| Async | Coroutines + Flow |
| Build | Kotlin DSL + Version Catalog |

## Performance

- Cold start: < 1.5 seconds
- No storage scans
- No polling loops
- No unnecessary services
- Minimal RAM usage in background
- Battery-optimized playback

## Building

### Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34

### Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Run tests
./gradlew testDebugUnitTest

# Code quality
./gradlew ktlintCheck detekt
```

## CI/CD

GitHub Actions workflows are included:

- **Build & Test**: Runs on every push/PR (lint, unit tests, assemble debug/release)
- **Release**: Triggered on version tags (builds signed APK/AAB, creates GitHub release)

See `.github/workflows/` for details.

## Privacy

This app is designed with privacy as a core principle:

- **No storage scanning**: We never scan your device storage
- **No file duplication**: Only URI references are stored
- **No network access**: The app works completely offline
- **No analytics**: No tracking or telemetry
- **Minimal permissions**: Only what's required for audio playback

## License

This project is open source and available under the MIT License.
