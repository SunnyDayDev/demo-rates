import org.gradle.internal.impldep.org.codehaus.plexus.util.PropertyUtils.loadProperties
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(RatesApp.targetSdk)
    buildToolsVersion = Android.buildToolVersion
    
    defaultConfig {
        applicationId = "dev.sunnyday.apps.rates"
        minSdkVersion(RatesApp.minSdk)
        targetSdkVersion(RatesApp.targetSdk)
        versionCode = 1
        versionName = "1.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    signingConfigs {
        create("release") {
            try {
                val properties = Properties()
                properties.load(file("signing.properties").inputStream())

                storeFile = project.file(properties["keystore.path"]!!)
                storePassword = properties["keystore.password"] as String
                keyAlias = properties["key.alias"] as String
                keyPassword = properties["key.password"] as String
            } catch (e: Throwable) {
                println("Error: $e")
                return@create
            }
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(Kotlin.stdlibJdk8)

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.activity)
    implementation(AndroidX.activityKtx)
    implementation(AndroidX.fragment)
    implementation(AndroidX.fragmentKtx)
    implementation(AndroidX.recyclerView)
    implementation(Kotlin.serialization)
    addDagger(useAndroid = true, useAsist = true, enableProcessing = true)
    addRx()
    addRetrofit()
    addOkHttp()
    addMobius()

    implementation(project(RatesApp.Core.common))
    implementation(project(RatesApp.Core.ui))
    implementation(project(RatesApp.Core.di))
    implementation(project(RatesApp.Domain.currencies))
    implementation(project(RatesApp.UI.rates))

    testImplementation(Test.junit)
    androidTestImplementation(Test.junitExt)
    androidTestImplementation(Test.espresso)
}