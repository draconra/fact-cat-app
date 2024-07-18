plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.search"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    //Module
    implementation(project(":Core-UI"))
    implementation(project(":Core-Network"))
    implementation(project(":History"))

    // Compose and UI
    implementation(libs.activityCompose)
    implementation(libs.composeUi)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.material3)
    implementation(libs.navigationCompose)
    implementation(libs.foundation)
    implementation(libs.material)

    // Dependency Injection
    implementation(libs.hiltAndroid)
    implementation(libs.hiltNavigation)
    kapt(libs.hiltAndroidCompiler)

    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
}