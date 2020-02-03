package dev.sunnyday.apps.rates.domain.currencies.service.network

import dev.sunnyday.apps.rates.domain.currencies.Currency
import dev.sunnyday.apps.rates.domain.currencies.service.CurrenciesService
import dev.sunnyday.apps.rates.domain.currencies.service.network.dto.DtoMapper
import io.reactivex.Single
import javax.inject.Inject

internal class CurrenciesNetworkService @Inject constructor(
    private val api: CurrenciesNetworkApi,
    private val mapper: DtoMapper
) : CurrenciesService {

    override fun getRates(base: Currency.Code): Single<Map<Currency.Code, Double>> =
        api.latest(base.name)
            .map(mapper::rates)

}