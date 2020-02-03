package dev.sunnyday.apps.rates.core.common

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T: Any> weak(): ReadWriteProperty<Any, T?> = WeakProperty()

private class WeakProperty<T: Any> : ReadWriteProperty<Any, T?> {

    private var ref: WeakReference<T>? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T? =
        ref?.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if (value === ref?.get()) return
        ref = value?.let(::WeakReference)
    }

}