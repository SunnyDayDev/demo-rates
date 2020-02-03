plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
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

    implementation(AndroidX.appCompat)
    implementation(AndroidX.material)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.activity)
    implementation(AndroidX.activityKtx)
    implementation(AndroidX.fragment)
    implementation(AndroidX.fragmentKtx)
    implementation(AndroidX.recyclerView)
    addMobius()

    testImplementation(Test.junit)
    androidTestImplementation(Test.junitExt)
    androidTestImplementation(Test.espresso)
}