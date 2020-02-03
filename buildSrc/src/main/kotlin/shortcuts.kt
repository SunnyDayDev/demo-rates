import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.addGlide() {
    add("implementation", Glide.api)
    add("kapt", Glide.compiler)
}

fun DependencyHandler.addRx() {
    add("implementation", Rx.java)
    add("implementation", Rx.android)
    add("implementation", Rx.kotlin)
}

fun DependencyHandler.addDagger(
    useAndroid: Boolean = false,
    useAsist: Boolean = true,
    enableProcessing: Boolean = true
) {
    add("implementation", Dagger.api)
    if (enableProcessing) {
        add("kapt", Dagger.compiler)
    }

    if (useAndroid) {
        add("implementation", Dagger.android)
        add("implementation", Dagger.androidSupport)
        if (enableProcessing) {
            add("kapt", Dagger.androidProcessor)
        }
    }

    if (useAsist) {
        add("compileOnly", Dagger.AssistedInject.api)
        if (enableProcessing) {
            add("kapt", Dagger.AssistedInject.processor)
        }
    }
}

fun DependencyHandler.addRetrofit() {
    add("implementation", Retrofit.api)
    add("implementation", Retrofit.rxJavaAdapater)
    add("implementation", Retrofit.kotinxSerializationConverter)
}

fun DependencyHandler.addOkHttp() {
    add("implementation", OkHttp.api)
    add("implementation", OkHttp.log)
}

fun DependencyHandler.addMobius() {
    add("implementation", Mobius.api)
    add("implementation", Mobius.rx2)
    add("implementation", Mobius.android)
    add("implementation", Mobius.extras)
    add("testImplementation", Mobius.test)
}