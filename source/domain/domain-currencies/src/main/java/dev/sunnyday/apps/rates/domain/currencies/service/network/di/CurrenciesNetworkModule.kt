package dev.sunnyday.apps.rates.domain.currencies.service.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.sunnyday.apps.rates.domain.currencies.service.CurrenciesService
import dev.sunnyday.apps.rates.domain.currencies.service.network.CurrenciesNetworkApi
import dev.sunnyday.apps.rates.domain.currencies.service.network.CurrenciesNetworkService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create
import timber.log.Timber

@Module(includes = [
    CurrenciesNetworkModule.BindsModule::class
])
internal class CurrenciesNetworkModule {

    @Provides
    fun provideLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) = Timber.v(message)
        })

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    @Provides
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        val mediaTypeJson = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(CurrenciesNetworkApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(Json.asConverterFactory(mediaTypeJson))
            .client(client)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): CurrenciesNetworkApi =
        retrofit.create()

    @Module
    interface BindsModule {

        @Binds
        fun bindNetworkService(impl: CurrenciesNetworkService): CurrenciesService

    }

}