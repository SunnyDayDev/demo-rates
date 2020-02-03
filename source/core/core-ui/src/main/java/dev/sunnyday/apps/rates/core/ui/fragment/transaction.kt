package dev.sunnyday.apps.rates.core.ui.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentTransaction

fun FragmentTransaction.add(
    @IdRes containerViewId: Int,
    fragment: FragmentName,
    tag: String? = null
) = add(containerViewId, fragment.klass, fragment.args, tag)