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
