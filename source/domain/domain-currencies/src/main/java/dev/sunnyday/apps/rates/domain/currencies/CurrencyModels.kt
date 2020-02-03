package dev.sunnyday.apps.rates.domain.currencies

import android.net.Uri

data class Currency(
    val code: Code,
    val name: String,
    val icon: Uri
) {

    override fun toString(): String = "Currency(code = ${code.name})"

    enum class Code {
        AUD, BGN, BRL, CAD, CHF, CNY, CZK, DKK, EUR, GBP, HKD, HRK, HUF, IDR, ILS, INR, ISK, JPY,
        KRW, MXN, MYR, NOK, NZD, PHP, PLN, RON, RUB, SEK, SGD, THB, TRY, USD, ZAR;

        companion object {

            fun parse(code: String): Code? = values().firstOrNull {
                it.name.equals(code, ignoreCase = true)
            }

        }

    }

}

class CurrencyRates internal constructor(
    private val currencies: Map<Currency.Code, Currency>,
    val rates: Map<Currency.Code, Double>
){

    fun getCurrency(currency: Currency.Code): Currency = currencies.getValue(currency)

}