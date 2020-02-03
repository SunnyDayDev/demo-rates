package dev.sunnyday.apps.rates.domain.currencies.di

import dagger.Binds
import dagger.Module
import dev.sunnyday.apps.rates.domain.currencies.CurrenciesRepository
import dev.sunnyday.apps.rates.domain.currencies.CurrenciesRepositoryImpl
import dev.sunnyday.apps.rates.domain.currencies.service.network.CurrenciesNetworkService
import dev.sunnyday.apps.rates.domain.currencies.service.CurrenciesService
import dev.sunnyday.apps.rates.domain.currencies.service.network.di.CurrenciesNetworkModule
import javax.inject.Singleton

@Module(includes = [CurrenciesNetworkModule::class])
abstract class CurrenciesDomainModule {

    @Binds
    @Singleton
    internal abstract fun bindRepository(impl: CurrenciesRepositoryImpl): CurrenciesRepository

}