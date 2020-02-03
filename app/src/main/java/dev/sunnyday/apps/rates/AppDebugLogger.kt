package dev.sunnyday.apps.rates

import android.util.Log
import timber.log.Timber

class AppDebugLogger(private val level: Int = Log.DEBUG) : Timber.DebugTree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean = priority >= level

}