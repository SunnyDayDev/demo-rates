package dev.sunnyday.apps.rates.ui.rates.effectHandlers

import dev.sunnyday.apps.rates.core.common.weak
import dev.sunnyday.apps.rates.core.di.PerFeature
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEffect.*
import javax.inject.Inject

@PerFeature
internal class RatesViewEffectHandler @Inject constructor(): RatesPartialEffectHandler {

    var view: View? by weak()

    override fun invoke(builder: RatesEffectHandlerBuilder): RatesEffectHandlerBuilder = builder
        .addAction(HideKeyboard::class.java, this::hideKeyboard)
        .addAction(ShowKeyboard::class.java, this::showKeyboard)

    private fun hideKeyboard() = withView {
        it.hideKeyboard()
    }

    private fun showKeyboard() = withView {
        it.showKeyboard()
    }

    private inline fun withView(action: (View) -> Unit) {
        view?.let(action)
    }

    interface View {

        fun hideKeyboard()

        fun showKeyboard()

    }

}

