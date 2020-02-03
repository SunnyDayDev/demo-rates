plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
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

    buildFeatures {
        viewBinding = true
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

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.fragment)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.coordinatorLayout)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.material)
    implementation(Views.circleImageView)
    addDagger(useAndroid = true, useAsist = true, enableProcessing = true)
    addRx()
    addMobius()
    addGlide()
    implementation(RxBindings.platform)

    implementation(project(RatesApp.Core.common))
    implementation(project(RatesApp.Core.ui))
    implementation(project(RatesApp.Core.di))
    implementation(project(RatesApp.Domain.currencies))

    testImplementation(Test.junit)
    androidTestImplementation(Test.junitExt)
    androidTestImplementation(Test.espresso)
}