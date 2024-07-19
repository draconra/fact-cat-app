plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.fact"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "META-INF/LICENSE.md"
        }
    }
}

dependencies {
    //Module
    implementation(project(":Core-UI"))
    implementation(project(":Core-Network"))

    //Core
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.google.material)

    // Compose and UI
    implementation(libs.activityCompose)
    implementation(libs.composeUi)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.material3)
    implementation(libs.navigationCompose)
    implementation(libs.foundation)
    implementation(libs.material)
    implementation(libs.lottie)

    // Dependency Injection
    implementation(libs.hiltAndroid)
    implementation(libs.hiltNavigation)
    kapt(libs.hiltAndroidCompiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.junitJupiterApi)
    androidTestImplementation(libs.junitExt)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.composeUiTestJunit4)
    androidTestImplementation(libs.composeUiTestManifest)
    androidTestImplementation(libs.coreTesting)
    testImplementation(libs.coreTesting)
    testImplementation(libs.coroutinesTest)
    androidTestImplementation(libs.mockkAndroid)

}