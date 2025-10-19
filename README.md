# Android Hello World App

A simple Android application demonstrating a "Hello World" screen built with Kotlin and modern Android development practices.

## Tech Stack

- **Language**: Kotlin 1.9.20
- **Build Tool**: Gradle 8.2 with Android Gradle Plugin 8.2.0
- **SDK**: Min 24 (Android 7.0) / Target 34 (Android 14)
- **UI**: XML layouts with ConstraintLayout
- **Libraries**: AndroidX Core KTX, AppCompat, Material Design Components

## Quick Start

### Build the App
```bash
./gradlew assembleDebug
```

### Run Tests
```bash
./gradlew test
```

### Install on Device/Emulator
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Project Structure

```
android-sample-app/
├── app/src/main/
│   ├── java/com/example/helloworld/
│   │   └── MainActivity.kt          # Main entry point
│   ├── res/layout/
│   │   └── activity_main.xml        # Main screen layout
│   └── AndroidManifest.xml
├── build.gradle                     # Build configuration
└── .claude/commands/                # Development commands
```

## Prerequisites

- **Android SDK** (install via command line tools or Android Studio)
- **Java 11+** for builds (set as JAVA_HOME)
- **AndroidX** enabled in gradle.properties

## Architecture & Best Practices

This project follows [Android's official architecture guidelines](https://developer.android.com/topic/architecture):
- Separation of concerns
- Resource externalization (string resources)
- ConstraintLayout for flexible UI

For production apps, see [Architecture Recommendations](https://developer.android.com/topic/architecture/recommendations) to learn about MVVM, dependency injection, navigation components, and more.

## License

This is a sample project for learning purposes.
