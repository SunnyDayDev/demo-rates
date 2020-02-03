package dev.sunnyday.apps.rates.domain.currencies.service.network.dto

import dev.sunnyday.apps.rates.domain.currencies.Currency
import javax.inject.Inject

internal class DtoMapper @Inject constructor() {

    fun rates(response: RatesResponseDto): Map<Currency.Code, Double> =
        response.rates
            .asSequence()
            .mapNotNull {
                val key = Currency.Code.parse(it.key)
                    ?: return@mapNotNull null
                key to it.value
            }
            .associate { it }

}