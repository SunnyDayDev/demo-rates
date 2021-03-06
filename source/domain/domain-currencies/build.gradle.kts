plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("kotlinx-serialization")
}

android {
    compileSdkVersion(RatesApp.targetSdk)

    defaultConfig {
        minSdkVersion(RatesApp.minSdk)
        targetSdkVersion(RatesApp.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        named("release"){
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }

    compileOptions {
        sourceCompatibility = Jvm.version
        targetCompatibility = Jvm.version
    }

    kotlinOptions {
        jvmTarget = Jvm.version.toString()
    }
}

dependencies {
    implementation(Kotlin.stdlibJdk8)

    addDagger()
    addRx()
    addRetrofit()
    addOkHttp()
    implementation(Kotlin.serialization)

    implementation(project(RatesApp.Core.common))

    testImplementation(Test.junit)
    androidTestImplementation(Test.junitExt)
    androidTestImplementation(Test.espresso)
}