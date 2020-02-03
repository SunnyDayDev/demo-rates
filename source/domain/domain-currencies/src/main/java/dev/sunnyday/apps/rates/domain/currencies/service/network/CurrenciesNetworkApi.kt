package dev.sunnyday.apps.rates.domain.currencies.service.network

import dev.sunnyday.apps.rates.domain.currencies.service.network.dto.RatesResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesNetworkApi {

    @GET("latest")
    fun latest(@Query("base") base: String): Single<RatesResponseDto>

    companion object {

        const val BASE_URL = "https://revolut.duckdns.org/"

    }

}