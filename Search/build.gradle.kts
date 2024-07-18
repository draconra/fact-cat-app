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

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junitJupiterEngine)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.composeUiTestJunit4)
    androidTestImplementation(libs.composeUiTestManifest)
    androidTestImplementation(libs.coreTesting)
    testImplementation(libs.coreTesting)
    testImplementation(libs.coroutinesTest)
    testRuntimeOnly(libs.junitVintage)
    androidTestImplementation(libs.androidJUnit5)
    androidTestImplementation(libs.androidJUnit5Runner)
    androidTestImplementation(libs.hiltAndroidTesting)
    androidTestImplementation(libs.mockkAndroid)
    kaptAndroidTest(libs.hiltAndroid)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}