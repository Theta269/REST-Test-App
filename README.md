# REST-Test-App

This is an example app of a very common Android use case for RESTful consumption & display of data 
with offline persistence.

## REQUIREMENTS

* Reads a JSON feed from the internet
* Parses it and shows the contents in a list
* Clicking on a list item shows a detailed view of that item
* Persists the contents of the feed locally, so if the app is used without Internet connection it will show previously downloaded content
* Compiles and runs from Android Studio

## Libraries

### HTTP & data parsing
**Retrofit** - Maintained by Square - Retrofit provides a convenient builder for constructing a data object through HTTP. It takes in a base URL which is going used for every service call, a converter factory for all of the parsing of data sent and also any responses recieved, and any HTTP client options required for successful calls.

Website - https://square.github.io/retrofit/

**Gson** - Maintained by Google - Used in the aforementioned converter factory, Gson is a Java serialization/deserialization library to convert Java Objects to JSON objects and vice versa.

Website - https://github.com/google/gson

### UI
**Jetpack Compose** - Maintained by Google - A declarative UI framework in Android. Compose is Android’s recommended modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Compose boasts less code, powerful tools, and intuitive Kotlin APIs.

Website - https://developer.android.com/jetpack/compose

**Coil** - Maintained by InstaCart - An acronym for Coroutine Image Loader, Coil is an image loading library for Android backed by Kotlin Coroutines. 
Benefits are as follows:
  * Fast - performs a number of optimizations including memory and disk caching, downsampling the image in memory, automatically pausing/cancelling requests, and more.
  * Lightweight: Coil adds ~2000 methods to your APK (for apps that already use OkHttp and Coroutines), which is comparable to Picasso and significantly less than Glide and Fresco.
  * Easy to use: Coil's API leverages Kotlin's language features for simplicity and minimal boilerplate.
  * Modern: Coil is Kotlin-first and uses modern libraries including Coroutines, OkHttp, Okio, and AndroidX Lifecycles.

Website - https://coil-kt.github.io/coil/

### Data Storage
**Jetpack ROOM** - Maintained by Google - An abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

Website - https://developer.android.com/jetpack/androidx/releases/room

### Annotation
**KSP** - Maintained by Google - Kotlin Symbol Processing (KSP) is an API that you can use to develop lightweight compiler plugins. KSP provides a simplified compiler plugin API that leverages the power of Kotlin while keeping the learning curve at a minimum. Compared to KAPT, annotation processors that use KSP can run up to 2x faster.
This is recommended best practice:
https://developer.android.com/build/migrate-to-ksp

Website - https://github.com/google/ksp

## Build Configuration
* Android Studio Giraffe 2022.3.1 Canary 11
* Java version 17 - Jetbrains Runtime 17.0.6
* Gradle version 8.1.1

Canary versions of Android studio were picked to support Jetpack Compose tooling and Gradle Version Catalog.
https://developer.android.com/studio/preview/features
https://developer.android.com/build/migrate-to-catalogs

## Troubleshooting
The gradle scripts should download Gradle & JDK versions automatically, but if for whatever reason they are not set up, one can find the JDK setup as follows:

Android Studio (or "File" on Windows) -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle

And if Gradle does not update, one can find the updater here:

Tools -> AGP Upgrade Assistant

if the project still will not build, there may be a compatibility issue with the version of Android Studio being used

https://developer.android.com/studio/releases#android_gradle_plugin_and_android_studio_compatibility
