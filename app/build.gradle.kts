plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "au.vu.nit3213.a4670761"
    compileSdk = 36

    defaultConfig {
        applicationId = "au.vu.nit3213.a4670761"
        minSdk = 24
        targetSdk = 36
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
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    dependencies {
        testImplementation("junit:junit:4.13.2")
        testImplementation("androidx.arch.core:core-testing:2.2.0")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
        testImplementation("org.mockito:mockito-core:5.12.0")
        testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
        testImplementation("com.google.truth:truth:1.4.4")

        androidTestImplementation("androidx.test.ext:junit:1.2.1")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

        implementation("androidx.navigation:navigation-fragment-ktx:2.8.0")
        implementation("androidx.navigation:navigation-ui-ktx:2.8.0")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
        implementation("androidx.datastore:datastore-preferences:1.1.1")
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
        implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
        implementation("com.google.android.material:material:1.12.0")
        implementation("com.google.dagger:hilt-android:2.51.1")
        kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    }
}

