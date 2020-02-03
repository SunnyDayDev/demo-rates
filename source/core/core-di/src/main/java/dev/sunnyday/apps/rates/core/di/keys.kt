package dev.sunnyday.apps.rates.core.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)

@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)