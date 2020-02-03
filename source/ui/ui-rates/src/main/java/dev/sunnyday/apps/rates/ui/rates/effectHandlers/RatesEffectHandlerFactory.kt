package dev.sunnyday.apps.rates.ui.rates.effectHandlers

import com.spotify.mobius.rx2.RxMobius
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEffect
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent
import io.reactivex.ObservableTransformer
import javax.inject.Inject

internal typealias RatesEffectHandlerBuilder =
        RxMobius.SubtypeEffectHandlerBuilder<RatesEffect, RatesEvent>

internal typealias RatesPartialEffectHandler =
        Function1<RatesEffectHandlerBuilder, RatesEffectHandlerBuilder>

internal class RatesEffectHandlerFactory @Inject constructor(
    private val partialBuilders: @JvmSuppressWildcards Set<RatesPartialEffectHandler>
) {

    fun create(): ObservableTransformer<RatesEffect, RatesEvent> =
        RxMobius.subtypeEffectHandler<RatesEffect, RatesEvent>()
            .let { builder ->
                var resultBuilder = builder
                partialBuilders.forEach { partialBuilder ->
                    resultBuilder = partialBuilder(resultBuilder)
                }
                resultBuilder
            }
            .build()

}