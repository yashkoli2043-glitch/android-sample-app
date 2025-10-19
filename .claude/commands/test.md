Build the Android app and run all unit tests. Verify that all tests pass successfully.

## Test Type

This command runs **Unit Tests** - Local JVM tests located in `src/test/` directory.

## Running Tests

### Run All Unit Tests
```bash
./gradlew test
```

### Run Tests for Specific Build Variant
```bash
./gradlew testDebugUnitTest
./gradlew testReleaseUnitTest
```

### Run Specific Test Class
```bash
./gradlew test --tests com.example.helloworld.ExampleUnitTest
```

### Clean Before Testing
```bash
./gradlew clean test
```

## Test Configuration

- **Test Framework**: JUnit (standard for Android)
- **Kotlin Version**: 1.9.20
- **Java Compatibility**: Java 8 (1.8)
- **JAVA_HOME**: Requires Java 11+ (for Gradle 8.2 and AGP 8.2.0)

## Test Output Locations

After running tests, reports are generated at:
- **HTML Report**: `app/build/reports/tests/testDebugUnitTest/index.html`
- **XML Results**: `app/build/test-results/testDebugUnitTest/`

## Test Results Analysis

Report the following:
- **Total tests run**
- **Passed** tests count
- **Failed** tests count (with failure details and stack traces)
- **Skipped/Ignored** tests count
- **Test execution time**
- **Any compilation errors** preventing tests from running
- **Deprecation warnings** or notices

## Additional Test Options

```bash
# Run with verbose output
./gradlew test --info

# Run with stack traces for failures
./gradlew test --stacktrace

# Continue testing even after failures
./gradlew test --continue
```

Provide a clear summary of all test results including any failures, warnings, or important notices.
