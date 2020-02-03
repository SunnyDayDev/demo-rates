@file:Suppress("MemberVisibilityCanBePrivate")

import org.gradle.api.JavaVersion

object Jvm {
    val version = JavaVersion.VERSION_1_8
}

object Kotlin {
    const val version = "1.3.61"
    const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"

    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0"
}

object Android {

    const val buildToolClassPath = "com.android.tools.build:gradle:4.0.0-alpha09"
    const val buildToolVersion = "29.0.2"

}

object AndroidX {

    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val coreKtx = "androidx.core:core-ktx:1.1.0"
    const val activity = "androidx.activity:activity:1.1.0"
    const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
    const val fragment = "androidx.fragment:fragment:1.2.0"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val material = "com.google.android.material:material:1.1.0-rc02"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val card = "androidx.cardview:cardview:1.0.0"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"

}

object RxBindings {

    private const val version = "3.1.0"

    const val platform = "com.jakewharton.rxbinding3:rxbinding:$version"

}

object Views {

    const val circleImageView = "de.hdodenhof:circleimageview:3.1.0"

}

object Glide {

    private const val version = "4.11.0"

    const val api = "com.github.bumptech.glide:glide:$version"
    const val compiler = "com.github.bumptech.glide:compiler:$version"

}

object Rx {

    const val java = "io.reactivex.rxjava2:rxjava:2.2.16"
    const val android = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val kotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"

}

object Dagger {

    private const val version = "2.26"

    const val api = "com.google.dagger:dagger:$version"
    const val android = "com.google.dagger:dagger-android:$version"
    const val androidSupport = "com.google.dagger:dagger-android-support:$version"
    const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    const val compiler = "com.google.dagger:dagger-compiler:$version"

    object AssistedInject {

        private const val version = "0.5.2"

        const val api = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        const val processor = "com.squareup.inject:assisted-inject-processor-dagger2:$version"

    }

}

object Retrofit {

    const val api = "com.squareup.retrofit2:retrofit:2.7.1"
    const val rxJavaAdapater = "com.squareup.retrofit2:adapter-rxjava2:2.7.1"
    const val kotinxSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.4.0"

}

object OkHttp {

    private const val version = "4.3.1"

    const val api = "com.squareup.okhttp3:okhttp:$version"
    const val log = "com.squareup.okhttp3:logging-interceptor:$version"

}

object Mobius {

    private const val version = "1.3.4"

    const val api = "com.spotify.mobius:mobius-core:$version"
    const val rx2 = "com.spotify.mobius:mobius-rx2:$version"
    const val android = "com.spotify.mobius:mobius-android:$version"
    const val extras = "com.spotify.mobius:mobius-extras:$version"
    const val test = "com.spotify.mobius:mobius-test:$version"

}

object Log {

    const val timber = "com.jakewharton.timber:timber:4.7.1"

}

object Test {

    const val junit = "junit:junit:4.12"
    const val junitExt = "androidx.test.ext:junit:1.1.1"
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"

}

object RatesApp {

    const val targetSdk = 29
    const val minSdk = 21

    object Core {

        const val common = ":core:core-common"
        const val ui = ":core:core-ui"
        const val di = ":core:core-di"

    }

    object Domain {

        const val currencies = ":domain:domain-currencies"

    }

    object UI {

        const val rates = ":ui:ui-rates"

    }

}