# Setup Instructions

## Prerequisites

1. **Android Studio**: Hedgehog (2023.1.1) or newer
2. **JDK**: Version 17
3. **Android SDK**: API 34
4. **Git**: For cloning the repository

## Clone and Build

```bash
# Clone the repository
git clone <repository-url>
cd MusicPlayer

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
```

## Project Import

1. Open Android Studio
2. Select "Open an Existing Project"
3. Choose the `MusicPlayer` directory
4. Wait for Gradle sync to complete
5. Run the app with Shift+F10 or the Run button

## Development Workflow

### Code Quality

```bash
# Run Kotlin linter
./gradlew ktlintCheck

# Run static analysis
./gradlew detekt

# Fix auto-fixable lint issues
./gradlew ktlintFormat
```

### Testing

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run all checks (lint + test + build)
./gradlew check
```

### Build Variants

| Variant | Command | Output |
|---------|---------|--------|
| Debug | `./gradlew assembleDebug` | `app/build/outputs/apk/debug/` |
| Release | `./gradlew assembleRelease` | `app/build/outputs/apk/release/` |
| Bundle | `./gradlew bundleRelease` | `app/build/outputs/bundle/release/` |

## Release Process

1. Update version in `app/build.gradle.kts`
2. Create a git tag: `git tag -a v1.0.0 -m "Version 1.0.0"`
3. Push the tag: `git push origin v1.0.0`
4. GitHub Actions will automatically build and create a release

### Manual Signing

For local release builds, add to `local.properties`:

```properties
SIGNING_KEYSTORE_PATH=/path/to/keystore.jks
SIGNING_KEYSTORE_PASSWORD=your_password
SIGNING_KEY_ALIAS=your_alias
SIGNING_KEY_PASSWORD=your_key_password
```

## CI/CD Setup

### GitHub Actions

The project includes two workflows in `.github/workflows/`:

1. **build.yml**: Runs on every push and PR
   - Lint checks (ktlint, detekt)
   - Unit tests
   - Assembles debug and release builds

2. **release.yml**: Triggered on version tags (`v*`)
   - Builds signed release APK and AAB
   - Uploads artifacts
   - Creates GitHub release

### Required Secrets (for release signing)

Add these secrets in GitHub repository settings:

- `SIGNING_KEYSTORE_BASE64`: Base64-encoded keystore file
- `SIGNING_KEY_ALIAS`: Key alias
- `SIGNING_KEY_PASSWORD`: Key password
- `SIGNING_STORE_PASSWORD`: Keystore password

## Troubleshooting

### Gradle Sync Issues

```bash
# Clean and rebuild
./gradlew clean build

# Refresh dependencies
./gradlew --refresh-dependencies
```

### Build Errors

- Ensure JDK 17 is set in Android Studio settings
- Check that Android SDK 34 is installed
- Verify Kotlin plugin is up to date

### IDE Configuration

Recommended Android Studio settings:
- Enable "Auto-import" for Kotlin
- Set code style to Kotlin style guide
- Enable trailing comma formatting
