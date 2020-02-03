package dev.sunnyday.apps.rates.ui.rates.loop

import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent
import dev.sunnyday.apps.rates.ui.rates.domain.RatesLogic
import dev.sunnyday.apps.rates.ui.rates.domain.RatesModel
import dev.sunnyday.apps.rates.ui.rates.effectHandlers.RatesEffectHandlerFactory
import javax.inject.Inject
import javax.inject.Provider

internal typealias RatesLoopController =
        @JvmSuppressWildcards MobiusLoop.Controller<RatesModel, RatesEvent>

internal class RatesLoopFactory @Inject constructor(
    private val logicProvider: Provider<RatesLogic>,
    private val effectHandlerBuilder: RatesEffectHandlerFactory
) {

    fun create(): RatesLoopController {
        val logic = logicProvider.get()
        val effectHandler = effectHandlerBuilder.create()

        return MobiusAndroid.controller(
            RxMobius.loop(logic, effectHandler),
            RatesModel(),
            logic
        )
    }

}