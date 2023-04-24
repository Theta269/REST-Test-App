plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.example.resttestapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.resttestapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    annotationProcessor(libs.androidx.room.compiler)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.ui.tooling)
    implementation (libs.coil.compose)
    implementation (libs.converter.gson)
    implementation(libs.activity.compose)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.core.ktx)
    implementation(libs.gson)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.material3)
    implementation(libs.retrofit)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.junit)
}