package dev.sunnyday.apps.rates.core.di

import androidx.fragment.app.Fragment

interface FragmentComponent<F: Fragment> {

    val fragment: F

    interface Factory<F: Fragment> {

        fun create(): FragmentComponent<F>

    }

}