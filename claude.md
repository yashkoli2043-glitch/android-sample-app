# Android Hello World App

## Project Overview

A simple Android application demonstrating a "Hello World" screen built with Kotlin and modern Android development practices.

## Tech Stack

- **Language**: Kotlin 1.9.20
- **Build Tool**: Gradle 8.2 with Android Gradle Plugin 8.2.0
- **SDK Versions**: Min 24 (Android 7.0) / Target 34 (Android 14)
- **Java Compatibility**: Java 8, requires Java 11+ for builds
- **UI Framework**: XML layouts with ConstraintLayout
- **Key Libraries**: AndroidX Core KTX, AppCompat, Material Design Components

## Project Structure

```
android-sample-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/helloworld/
│   │   │   │   └── MainActivity.kt          # Main entry point
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml    # Main screen layout
│   │   │   │   ├── values/
│   │   │   │   │   └── strings.xml          # String resources
│   │   │   │   └── ...
│   │   │   └── AndroidManifest.xml          # App configuration
│   │   └── test/                            # Unit tests
│   └── build.gradle                         # App-level build config
├── gradle/
│   └── wrapper/                             # Gradle wrapper files
├── build.gradle                             # Root build config
├── settings.gradle                          # Project settings
├── gradle.properties                        # Gradle properties
├── CLAUDE.md                                # This file
└── .claude/
    └── commands/                            # Slash commands
        ├── build.md
        ├── test.md
        └── debug.md
```

## Package Information

- **Package Name**: `com.example.helloworld`
- **Application ID**: `com.example.helloworld`
- **Version**: 1.0 (versionCode: 1)

## Build Commands

### Build Debug APK
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

### Run Unit Tests
```bash
./gradlew test
```
Reports: `app/build/reports/tests/testDebugUnitTest/index.html`

### Clean Build
```bash
./gradlew clean
```

### Prerequisites

- **Android SDK** installed via command line tools or sdkmanager
- **Java 11+** set as JAVA_HOME (required for Gradle 8.2 and AGP 8.2.0)
- **AndroidX** enabled in gradle.properties

## Running the App

### Option 1: Local Emulator (mobile-mcp)
The project is configured with mobile-mcp MCP server for local emulator testing:
- List/launch emulators and devices
- Install and launch the app
- Take screenshots and inspect UI elements

### Option 2: BrowserStack (browserstack-mcp)
Test on real devices in the cloud using browserstack-mcp MCP server:
- Launch live testing sessions on real Android/iOS devices
- Automatic app upload, installation, and launch
- Test across multiple device configurations

### Option 3: Manual ADB
```bash
# Start emulator
emulator -avd <avd_name> &

# Install and run
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.example.helloworld/.MainActivity
```

## Android Architecture & Best Practices

This project follows Android's official architecture recommendations. For detailed guidance, refer to:

- **[Guide to App Architecture](https://developer.android.com/topic/architecture)** - Official Android architecture guide
- **[Architecture Recommendations](https://developer.android.com/topic/architecture/recommendations)** - Best practices for building robust apps

### Key Principles

1. **Separation of Concerns** - Keep UI and business logic separate
2. **Layered Architecture** - Organize code into UI, Domain (optional), and Data layers
3. **Drive UI from Data Models** - Use unidirectional data flow (UDF)
4. **Single Source of Truth** - Maintain one authoritative data source
5. **Reactive Programming** - Use StateFlow, LiveData, or similar patterns

### Current Architecture

This simple app uses:
- **UI Layer**: MainActivity (Activity) + XML layout (View)
- **Resource Management**: String resources for externalized text
- **ConstraintLayout**: For flexible, performant UI design

### Recommended Enhancements

For production apps, consider:
- **Modern UI**: Migrate to Jetpack Compose for declarative UI
- **Architecture Pattern**: Implement MVVM or MVI with ViewModel
- **Dependency Injection**: Use Hilt or Koin
- **Navigation**: Implement Navigation Component for multi-screen apps
- **Testing**: Add unit tests (JUnit) and UI tests (Espresso)
- **Persistence**: Use Room for local database, Retrofit for networking
- **Modularization**: Split app into feature modules for scalability

## Development Guidelines

- Use slash commands `/build`, `/test`, `/debug` for common tasks
- Reference string resources (strings.xml) instead of hardcoding text
- Follow Kotlin coding conventions and Android style guide
- Enable AndroidX in gradle.properties
- Test on multiple API levels (min SDK 24, target SDK 34)
