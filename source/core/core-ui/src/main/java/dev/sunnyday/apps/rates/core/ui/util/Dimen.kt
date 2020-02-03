package dev.sunnyday.apps.rates.core.ui.util

import android.content.Context
import android.util.TypedValue

object Dimen {

    fun dp(value: Number, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), metrics)
    }

}