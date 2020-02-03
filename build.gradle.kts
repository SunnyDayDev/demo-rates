// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Android.buildToolClassPath)
        classpath(kotlin("gradle-plugin", version = Kotlin.version))
        classpath(kotlin("serialization", version = Kotlin.version))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    delete("build")
}