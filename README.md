[![codecov](https://codecov.io/gh/tfandkusu/android_app_template/branch/main/graph/badge.svg?token=DQI5AN5H0Q)](https://codecov.io/gh/tfandkusu/android_app_template)

# 【WIP】Android app template


# Functionality

This repository is a template for Android app.
So it does not have any practical features.

It displays a list of [tfandkusu](https://github.com/tfandkusu)'s public GitHub repositories.

<img src="https://user-images.githubusercontent.com/16898831/146685977-85ab807c-bb04-4378-b005-71c7ecb9566c.png" width="200">

# Technology used

All libraries used are defined in [lib.versions.toml](https://github.com/tfandkusu/android_app_template/blob/main/gradle/libs.versions.toml)

**Ref:** [The version catalog TOML file format](https://docs.gradle.org/7.0.2/userguide/platforms.html#sub::toml-dependencies-format)

## View layer

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

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

- [Jacoco](https://www.eclemma.org/jacoco/)
- [Codecov](https://about.codecov.io/)

## CI/CD

- [GitHub Actions](https://github.co.jp/features/actions)
- [Spotless plugin for Gradle](https://github.com/diffplug/spotless/tree/main/plugin-gradle)

# References

- [UnidirectionalViewModel](https://github.com/DroidKaigi/conference-app-2021/blob/main/uicomponent-compose/core/src/main/java/io/github/droidkaigi/feeder/core/UnidirectionalViewModel.kt) from [DroidKaigi/conference-app-2021](https://github.com/DroidKaigi/conference-app-2021)
