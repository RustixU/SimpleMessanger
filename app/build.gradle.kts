plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    id("com.google.devtools.ksp")
}

android {
    namespace = "rut.miit.simplemessanger"
    compileSdk = 34

    defaultConfig {
        applicationId = "rut.miit.simplemessanger"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // DataStore для работы с Preferences
    implementation(libs.androidx.datastore.preferences)

    // Kotlin Coroutines для работы с DataStore
    implementation(libs.kotlinx.coroutines.android)

    // AppCompat и другие библиотеки
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.androidx.core.ktx.v1101)

//    navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

//    retrofit
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter.v100)
    implementation(libs.okhttp)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.room.runtime.v252)
    ksp(libs.androidx.room.compiler)

    // Для поддержки Kotlin Flow
    implementation(libs.androidx.room.ktx.v252)

    // Kotlin стандартные библиотеки
    implementation(libs.kotlin.stdlib)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}