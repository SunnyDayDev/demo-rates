package dev.sunnyday.apps.rates.domain.currencies

import javax.inject.Inject


internal class CurrenciesMapper @Inject constructor(
    private val currencyFactory: CurrencyFactory
) {

    fun mapRatesToCurrencyRates(rates: Map<Currency.Code, Double>): CurrencyRates {
        val currencies = rates.mapValues { (code, _) ->
            mapCodeToCurrency(code)
        }
        return CurrencyRates(currencies, rates)
    }

    private fun mapCodeToCurrency(code: Currency.Code): Currency =
        currencyFactory.create(code)

}