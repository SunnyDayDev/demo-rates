package dev.sunnyday.apps.rates.ui.rates.effectHandlers

import dev.sunnyday.apps.rates.core.ui.mobius.addTransformer
import dev.sunnyday.apps.rates.core.ui.mobius.switch
import dev.sunnyday.apps.rates.domain.currencies.CurrenciesRepository
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEffect.*
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent.*
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class RatesDomainEffectHandler @Inject constructor(
    private val repository: CurrenciesRepository
): RatesPartialEffectHandler {

    override fun invoke(builder: RatesEffectHandlerBuilder): RatesEffectHandlerBuilder = builder
        .addTransformer(switch(this::fetchRates))

    private fun fetchRates(effect: FetchRates): Observable<RatesEvent> {
        var isAtLeastOneValueFetched = false

        return repository.getRatesWithUpdates(effect.base)
            .map<RatesEvent> { RatesFetched(effect.base, it) }
            .doOnNext { isAtLeastOneValueFetched = true }
            .retryWhen { errors ->
                errors.switchMapSingle {
                    if (isAtLeastOneValueFetched) Single.timer(1, TimeUnit.SECONDS)
                    else Single.error(it)
                }
            }
            .onErrorReturn(::RatesFetchFailed)
    }

}