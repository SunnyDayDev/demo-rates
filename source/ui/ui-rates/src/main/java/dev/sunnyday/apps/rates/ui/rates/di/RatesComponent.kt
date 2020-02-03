package dev.sunnyday.apps.rates.ui.rates.di

import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dev.sunnyday.apps.rates.core.di.FragmentComponent
import dev.sunnyday.apps.rates.core.di.PerFeature
import dev.sunnyday.apps.rates.core.di.ViewModelKey
import dev.sunnyday.apps.rates.core.ui.collection.Differ
import dev.sunnyday.apps.rates.ui.rates.RatesFragment
import dev.sunnyday.apps.rates.ui.rates.domain.CurrencyRateItem
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent
import dev.sunnyday.apps.rates.ui.rates.effectHandlers.RatesDomainEffectHandler
import dev.sunnyday.apps.rates.ui.rates.effectHandlers.RatesPartialEffectHandler
import dev.sunnyday.apps.rates.ui.rates.effectHandlers.RatesViewEffectHandler
import dev.sunnyday.apps.rates.ui.rates.loop.RatesLoopController
import dev.sunnyday.apps.rates.ui.rates.loop.RatesLoopFactory
import dev.sunnyday.apps.rates.ui.rates.loop.RatesLoopViewModel
import dev.sunnyday.apps.rates.ui.rates.view.RatesItemsDiffer
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

@PerFeature
@Subcomponent(modules = [RatesModule::class])
interface RatesFragmentComponent : FragmentComponent<RatesFragment> {

    @Subcomponent.Factory
    interface Factory: FragmentComponent.Factory<RatesFragment>

}

@AssistedModule
@Module(includes = [AssistedInject_RatesModule::class])
internal abstract class RatesModule {

    @Binds
    @IntoMap
    @ViewModelKey(RatesLoopViewModel::class)
    abstract fun bindRatesLoopViewModel(vm: RatesLoopViewModel): ViewModel

    @Binds
    abstract fun bindEventsObserver(impl: Subject<RatesEvent>): Observer<RatesEvent>

    @Binds
    abstract fun bindEventsObservable(impl: Subject<RatesEvent>): Observable<RatesEvent>

    @Binds
    @IntoSet
    abstract fun bindDomainEffectHandler(impl: RatesDomainEffectHandler): RatesPartialEffectHandler

    @Binds
    @IntoSet
    abstract fun bindViewEffectHandlerBuilder(impl: RatesViewEffectHandler): RatesPartialEffectHandler

    @Binds
    abstract fun bindItemsDiffer(impl: RatesItemsDiffer): Differ<CurrencyRateItem>

    companion object {

        @Provides
        @PerFeature
        fun eventsSubject(): Subject<RatesEvent> = PublishSubject.create()

        @Provides
        fun ratesLoopController(factory: RatesLoopFactory): RatesLoopController = factory.create()

    }

}