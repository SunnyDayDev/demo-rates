package dev.sunnyday.apps.rates.di

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.*
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.sunnyday.apps.rates.App
import dev.sunnyday.apps.rates.AppActivity
import dev.sunnyday.apps.rates.core.di.FragmentComponent
import dev.sunnyday.apps.rates.core.di.FragmentKey
import dev.sunnyday.apps.rates.core.di.PerActivity
import dev.sunnyday.apps.rates.domain.currencies.di.CurrenciesDomainModule
import dev.sunnyday.apps.rates.ui.rates.RatesFragment
import dev.sunnyday.apps.rates.ui.rates.di.RatesFragmentComponent
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        CurrenciesDomainModule::class,
        AppActivityModule::class,
        FragmentsModule::class,
        AndroidInjectionModule::class
    ])
internal interface AppComponent: AndroidInjector<App> {

    val rates: RatesFragmentComponent.Factory

    @Component.Factory
    interface Factory: AndroidInjector.Factory<App> {

        override fun create(@BindsInstance instance: App): AppComponent

    }

}

@Module
internal interface AppModule {

    @Binds
    fun context(app: App): Context

}

@Module
internal interface AppActivityModule {

    @PerActivity
    @ContributesAndroidInjector
    fun contributeActivity(): AppActivity

}

@Module
internal interface FragmentsModule {

    @Binds
    @IntoMap
    @FragmentKey(RatesFragment::class)
    fun rates(factory: RatesFragmentComponent.Factory): FragmentComponent.Factory<out Fragment>

}