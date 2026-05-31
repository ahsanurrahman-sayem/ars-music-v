# Architecture Documentation

## Overview

The Music Player app follows **Clean Architecture** principles combined with **MVVM** presentation pattern. The codebase is organized into modular layers with clear dependency rules.

## Architecture Layers

### 1. Presentation Layer (Feature Modules)

- **Responsibility**: UI rendering and user interaction handling
- **Components**: Composable screens, ViewModels
- **Dependencies**: Domain layer (use cases), UI layer (shared components)
- **Rules**:
  - ViewModels only interact with use cases
  - No direct access to repositories or data sources
  - UI state is immutable and driven by StateFlow

### 2. Domain Layer

- **Responsibility**: Business logic and use cases
- **Components**: Use cases, repository interfaces, PlaybackController interface
- **Dependencies**: Model layer
- **Rules**:
  - Contains no Android framework dependencies
  - Defines repository interfaces (implemented by data layer)
  - Encapsulates all business logic in use cases

### 3. Data Layer

- **Responsibility**: Data persistence and retrieval
- **Components**: Room database, DAOs, repository implementations, DataStore
- **Dependencies**: Model layer
- **Rules**:
  - Implements repository interfaces defined in domain layer
  - Handles all data operations (CRUD, search, etc.)
  - Manages URI permissions for content URIs

### 4. Model Layer

- **Responsibility**: Data models and entities
- **Components**: Data classes, enums, sealed classes
- **Dependencies**: None (pure Kotlin)
- **Rules**:
  - No business logic
  - Serializable for potential future sync

## Module Structure

```
:app                    Application module (navigation, DI, manifest)
:core:model             Entities and data models
:core:common            Utilities, Result type, time formatting
:core:data              Room database, DAOs, repositories, DataStore
:core:domain            Use cases, repository interfaces
:core:media             Media3 ExoPlayer, service, notification, audio focus
:core:ui                Shared Compose components, theme, colors
:feature:library        Library screen (track list, search, import)
:feature:player         Full-screen player with controls
:feature:playlist       Playlist CRUD operations
:feature:favorites      Favorite tracks management
:feature:queue          Playback queue display
:feature:recent         Recently played history
:feature:settings       App preferences and configuration
```

## Dependency Rules

```
Feature modules -> Domain + UI modules
Domain module   -> Model module
Data module     -> Model + Common modules
Media module    -> Domain + Data modules
UI module       -> Model module
```

Dependencies always flow inward. Outer layers depend on inner layers, never the reverse.

## Data Flow

```
User Action -> ViewModel -> Use Case -> Repository -> DAO -> Room DB
                                              -> DataStore (for settings)
                ^                            |
                |____ StateFlow <____________|
```

## Key Design Decisions

### Storage Strategy

The app stores only metadata and URI references:
- Audio files are never copied or duplicated
- Content URIs are used with persistable permissions
- Custom cover art stores only the image URI
- Custom backgrounds store only the image URI

### Media Playback Architecture

- **ExoPlayer**: Single instance managed by MediaPlayerController
- **Foreground Service**: PlaybackService maintains notification and survives backgrounding
- **MediaSession**: Integrates with system media controls (lockscreen, Bluetooth, widgets)
- **Audio Focus**: Proper ducking and pausing behavior via AudioFocusManager

### State Management

- **Playback State**: Single source of truth via StateFlow in MediaPlayerController
- **UI State**: Each screen has its own UiState data class
- **Settings**: Persisted via DataStore and exposed as Flow

## Testing Strategy

### Unit Tests
- ViewModels: Mock use cases, verify state changes
- Use Cases: Mock repositories, verify business logic
- Repositories: Mock DAOs, verify data operations

### Integration Tests
- Database operations with in-memory Room
- Media playback with test audio files

### UI Tests
- Compose screen tests with fake data
- Navigation flow tests

## Performance Considerations

- Lazy loading of track lists with pagination
- Image loading via Coil with caching
- Position updates at 5Hz (200ms) for smooth seek bar
- Coroutines for all async operations
- No memory leaks: proper lifecycle management in ViewModels and Service
