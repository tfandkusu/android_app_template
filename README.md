[![codecov](https://codecov.io/gh/tfandkusu/android_app_template/branch/main/graph/badge.svg?token=DQI5AN5H0Q)](https://codecov.io/gh/tfandkusu/android_app_template)

# Android app template

# Functionality

This repository is a template for Android app.
So it does not have any practical features.

It displays a list of [tfandkusu](https://github.com/tfandkusu)'s public GitHub repositories.
And users can like the repository.

<img src="https://user-images.githubusercontent.com/16898831/210083599-134c9d3c-0ee1-435e-a655-9f9cf12c35e1.png" width="200"> <img src="https://user-images.githubusercontent.com/16898831/210083609-9e5122aa-8f1a-46e1-9143-f477f460cb22.png" width="200">

This app supports [dynamic color](https://m3.material.io/styles/color/dynamic-color/overview), light and dark theme.

# Install

Current main branch.

[<img src="https://dply.me/tfafbv/button/large" alt="Try it on your device via DeployGate">](https://dply.me/tfafbv#install)

# Architecture

The 3 layers described in [Android recommended app architecture](https://developer.android.com/jetpack/guide#recommended-app-arch)

- UI Layer
    - [Jetpack Compose](https://developer.android.com/jetpack/compose)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- Domain Layer
- Data Layer
    - Repository
    - Data Sources
        - [Retrofit](https://github.com/square/retrofit)
        - [Room](https://developer.android.com/jetpack/androidx/releases/room)

## ViewModel

![image](https://user-images.githubusercontent.com/16898831/212493372-7459e52f-a9ec-424c-8379-bc032dc09e90.png)

Redux is used for ViewModel

### Event

- User operation and lifecycle event.

### State

- State of compose
- Compose is rendered by state.

### Effect

- One shot operation such as navigation and snackbar.

### ActionCreator

- Access UseCases
- Dispatch actions

### Reducer

- Receive current state and action.
- Return next state and effect.

# Module structure

```mermaid
graph TD;
    app-->feature:*:compose;
    app-->feature:*:presentation;
    app-->viewCommon;
    feature:*:presentation-->feature:*:usecase;
    feature:*:presentation-->feature:*:compose;
    feature:*:compose-->viewCommon;
    feature:*:presentation-->viewCommon;
    feature:*:usecase-->data:repository;
    data:repository-->data:remote;
    data:repository-->data:local;
```

- Multiple `compose`, `presentation`, and  `usecase`  modules will be created for each feature.
- All modules use `common` module.

## app

- Activity
- Compose navigation host

## feature:*:compose

It has minimum dependency to speed up compose preview.

- Compose
- Compose preview
- ViewModel interface
- ViewModel implementation for compose preview

## feature:*:presentation

- ViewModel implementation including ActionCreator and Reducer for production.

## viewCommon

- Theme
- Common interface for ViewModel, ActionCreator and Reducer
- Common composable functions
- Common API error handling

## feature:*:usecase

- Domain layer

## data:repository

- Represents the data layer

## data:local

- Use room to save data locally.

## data:remote

- Use Retrofit to access REST API.

## common

- Classes used by all modules.
    - Data class
    - Exception
    - Utility method

# Technology used

All libraries used are defined in [lib.versions.toml](https://github.com/tfandkusu/android_app_template/blob/main/gradle/libs.versions.toml)

**Ref:** [The version catalog TOML file format](https://docs.gradle.org/7.0.2/userguide/platforms.html#sub::toml-dependencies-format)

## View layer

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Compose Material 3](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- [RecomposeHighlighter](https://github.com/android/snippets/blob/master/compose/recomposehighlighter/src/main/java/com/example/android/compose/recomposehighlighter/RecomposeHighlighter.kt)

## Presentation layer

- [androidx.compose.runtime:runtime-livedata](https://developer.android.com/jetpack/compose/libraries#streams)

## Data layer

- [Retrofit](https://github.com/square/retrofit)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)

## DI

- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Hilt Navigation Compose](https://developer.android.com/jetpack/compose/libraries#hilt-navigation)

## Unit test

- [MockK](https://github.com/mockk/mockk)
- [Kotest](https://github.com/kotest/kotest)
- [Robolectric](http://robolectric.org/)

## Coverage

- [Kover](https://github.com/Kotlin/kotlinx-kover)
- [Codecov](https://about.codecov.io/)

## CI/CD

- [GitHub Actions](https://docs.github.com/actions)
- [gradle-build-action](https://github.com/gradle/gradle-build-action)
- [Spotless plugin for Gradle](https://github.com/diffplug/spotless/tree/main/plugin-gradle)
- [Danger](https://danger.systems/ruby/)
- [danger-android_lint](https://github.com/loadsmart/danger-android_lint)
- [Renovate](https://www.whitesourcesoftware.com/free-developer-tools/renovate/)
- [Firebase App Distribution](https://firebase.google.com/docs/app-distribution)
- [DeployGate](https://deploygate.com/)

## Other

- [OSS Licenses Gradle Plugin](https://github.com/google/play-services-plugins/tree/master/oss-licenses-plugin)
- [Timber](https://github.com/JakeWharton/timber)

# References

- [UnidirectionalViewModel](https://github.com/DroidKaigi/conference-app-2021/blob/main/uicomponent-compose/core/src/main/java/io/github/droidkaigi/feeder/core/UnidirectionalViewModel.kt) from [DroidKaigi/conference-app-2021](https://github.com/DroidKaigi/conference-app-2021)
