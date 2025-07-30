plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.activitycast"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.activitycast"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    val room_version = "2.7.2"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    val lifecycle_version = "2.9.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("androidx.compose.material:material:1.8.3")
    implementation("com.google.android.material:material:1.12.0")

    val work_version = "2.10.2"
    implementation("androidx.work:work-runtime:$work_version")
}