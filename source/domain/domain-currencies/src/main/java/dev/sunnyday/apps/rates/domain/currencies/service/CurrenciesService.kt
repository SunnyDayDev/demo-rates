package dev.sunnyday.apps.rates.domain.currencies.service

import dev.sunnyday.apps.rates.domain.currencies.Currency
import io.reactivex.Single

internal interface CurrenciesService {

    fun getRates(base: Currency.Code): Single<Map<Currency.Code, Double>>

}