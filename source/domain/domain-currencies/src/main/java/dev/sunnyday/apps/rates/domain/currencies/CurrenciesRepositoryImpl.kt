package dev.sunnyday.apps.rates.domain.currencies

import dev.sunnyday.apps.rates.domain.currencies.service.CurrenciesService
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class CurrenciesRepositoryImpl @Inject constructor(
    private val service: CurrenciesService,
    private val mapper: CurrenciesMapper
) : CurrenciesRepository {

    override fun getRatesWithUpdates(
        base: Currency.Code
    ): Observable<CurrencyRates> = Observable.defer {
        var lastHandledRequest = -1L

        Observable.interval(0, 1, TimeUnit.SECONDS)
            .flatMapMaybe { requestId ->
                getRates(base)
                    .filter { requestId > lastHandledRequest }
                    .doOnSuccess { lastHandledRequest = requestId }
                    .onErrorResumeNext { error: Throwable ->
                        if (requestId > lastHandledRequest) {
                            Maybe.error(error)
                        } else {
                            Maybe.empty()
                        }
                    }
            }
    }

    private fun getRates(base: Currency.Code): Single<CurrencyRates> =
        service.getRates(base)
            .map { rates ->
                val fullRates = (rates + (base to 1.0)).toSortedMap()
                mapper.mapRatesToCurrencyRates(fullRates)
            }

}