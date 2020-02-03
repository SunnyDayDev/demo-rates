package dev.sunnyday.apps.rates.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dev.sunnyday.apps.rates.core.di.FragmentComponent
import javax.inject.Inject

typealias FragmentClass = Class<out Fragment>
typealias FragmentComponentFactory = @JvmSuppressWildcards FragmentComponent.Factory<out Fragment>

internal class AppFragmentFactory @Inject constructor(
    private val factories: Map<FragmentClass, FragmentComponentFactory>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)

        val fragmentComponentFactory = factories[fragmentClass]
            ?: return super.instantiate(classLoader, className)

        return fragmentComponentFactory.create().fragment
    }

}