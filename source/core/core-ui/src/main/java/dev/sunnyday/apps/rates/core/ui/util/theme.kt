package dev.sunnyday.apps.rates.core.ui.util

import android.content.res.Resources
import android.util.TypedValue

fun Resources.Theme.resolveColorAttribute(colorAttr: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(colorAttr, typedValue, true)
    return typedValue.data
}