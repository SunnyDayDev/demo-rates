package dev.sunnyday.apps.rates.core.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FragmentName @PublishedApi internal constructor(
    internal val klass: Class<out Fragment>,
    internal val args: Bundle?
) : Parcelable {

    companion object {

        inline fun <reified F: Fragment> create(argsCreator: ((Bundle) -> Unit)): FragmentName =
            FragmentName(F::class.java, Bundle().apply(argsCreator))

        inline fun <reified F: Fragment> create(): FragmentName =
            FragmentName(F::class.java, null)

    }

}