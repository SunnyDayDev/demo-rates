package dev.sunnyday.apps.rates.domain.currencies

import io.reactivex.Observable


interface CurrenciesRepository {

    fun getRatesWithUpdates(
        base: Currency.Code
    ): Observable<CurrencyRates>

}