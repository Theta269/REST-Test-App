# REST-Test-App

This is an example app of a very common Android use case for RESTful consumption & display of data 
with offline persistence.

In this example, Retrofit & Gson will be leveraged for REST operations & parsing, 
Jetpack Compose & Coil will be used for UI & image rendering, and data will be stored with ROOM.

#### REQUIREMENTS

* Reads a JSON feed from the internet
* Parses it and shows the contents in a list
* Clicking on a list item shows a detailed view of that item
* Persists the contents of the feed locally, so if the app is used without Internet connection it will show previously downloaded content
* Compiles and runs from Android Studio

#### Build Configuration
* Android Studio Giraffe 2022.3.1 Canary 11
* Java version 17 - Jetbrains Runtime 17.0.6
* Gradle version 8.1.1

The gradle scripts should download these Gradle & JDK versions automatically, but if for whatever reason they are not set up, one can find the JDK setup as follows:

Android Studio (or "File" on Windows) -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle

And if Gradle does not update, one can find the updater here:

Tools -> AGP Upgrade Assistant

if the project still will not build, there may be a compatibility issue with the version of Android Studio being used

https://developer.android.com/studio/releases#android_gradle_plugin_and_android_studio_compatibility
