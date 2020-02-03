package dev.sunnyday.apps.rates

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.sunnyday.apps.rates.di.DaggerAppComponent
import timber.log.Timber

internal class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        configureLog()
    }

    private fun configureLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(AppDebugLogger())
        }
    }

}