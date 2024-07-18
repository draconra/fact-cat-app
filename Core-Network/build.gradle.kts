plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise.corenetwork"
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
}

dependencies {

    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junitExt)
    androidTestImplementation(libs.espressoCore)

    // DataStore and Protobuf
    implementation(libs.datastorePreferences)
    implementation(libs.datastore)
    implementation(libs.protobufKotlinLite)

    // Networking and Serialization
    implementation(libs.kotlinxSerializationJson)
    implementation(libs.retrofitConverter)
    implementation(libs.okhttp)
    implementation(libs.retrofit)

    // Dependency Injection
    implementation(libs.hiltAndroid)
    implementation(libs.hiltNavigation)
    kapt(libs.hiltAndroidCompiler)

    // Debugging
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chuckerNoOp)
}