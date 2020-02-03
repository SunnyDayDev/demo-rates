package dev.sunnyday.apps.rates.core.common.collection

inline fun <T> List<T>.mutated(mutator: MutableList<T>.() -> Unit): List<T> =
    toMutableList().apply(mutator).toList()