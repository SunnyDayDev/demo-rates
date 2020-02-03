package dev.sunnyday.apps.rates.ui.rates.loop

import androidx.lifecycle.ViewModel
import dev.sunnyday.apps.rates.ui.rates.effectHandlers.RatesViewEffectHandler
import dev.sunnyday.apps.rates.ui.rates.loop.RatesLoopController
import javax.inject.Inject

internal class RatesLoopViewModel @Inject constructor(
    val loopController: RatesLoopController,
    val viewEffectHandler: RatesViewEffectHandler
) : ViewModel()