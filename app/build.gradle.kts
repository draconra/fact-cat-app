plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleService)
    kotlin("kapt")
    alias(libs.plugins.hilt)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise"
    compileSdk = 34

    defaultConfig {
        applicationId = "jp.speakbuddy.edisonandroidexercise"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjvm-default=all")
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

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:22.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    // Core and Kotlin
    implementation(libs.coreKtx)
    implementation(libs.lifecycleRuntime)
    implementation(libs.liveDataRuntime)

    // Compose and UI
    implementation(libs.activityCompose)
    implementation(libs.composeUi)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.material3)
    implementation(libs.navigationCompose)
    implementation(libs.foundation)
    implementation(libs.material)
    implementation(libs.lottie)

    // DataStore and Protobuf
    implementation(libs.datastorePreferences)
    implementation(libs.datastore)
    implementation(libs.protobufKotlinLite)

    // Dependency Injection
    implementation(libs.hiltAndroid)
    implementation(libs.hiltNavigation)
    kapt(libs.hiltAndroidCompiler)

    // Networking and Serialization
    implementation(libs.kotlinxSerializationJson)
    implementation(libs.retrofitConverter)
    implementation(libs.okhttp)
    implementation(libs.retrofit)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.crashlytics)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junitJupiterEngine)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.composeUiTestJunit4)
    androidTestImplementation(libs.coreTesting)
    testImplementation(libs.robolectric)
    testImplementation(libs.coreTesting)
    testImplementation(libs.coroutinesTest)
    testRuntimeOnly(libs.junitVintage)
    androidTestImplementation(libs.androidJUnit5)
    androidTestImplementation(libs.androidJUnit5Runner)
    androidTestImplementation(libs.hiltAndroidTesting)
    androidTestImplementation(libs.mockkAndroid)
    kaptAndroidTest(libs.hiltAndroid)

    // Debugging
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chuckerNoOp)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}