package dev.sunnyday.apps.rates.ui.rates.domain

import android.net.Uri
import com.spotify.mobius.First
import com.spotify.mobius.Init
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import dev.sunnyday.apps.rates.domain.currencies.Currency
import dev.sunnyday.apps.rates.domain.currencies.CurrencyRates


internal typealias RatesInit= Init<RatesModel, RatesEffect>
internal typealias RatesFirst = First<RatesModel, RatesEffect>
internal typealias RatesUpdate = Update<RatesModel, RatesEvent, RatesEffect>
internal typealias RatesNext = Next<RatesModel, RatesEffect>

// region: Model(State)

internal data class RatesModel(
    val base: Currency.Code = Currency.Code.RUB,
    val rates: Map<Currency.Code, Double> = emptyMap(),
    val items: List<CurrencyRateItem> = emptyList(),
    val contentState: ContentState = ContentState.LOADING
)

internal data class CurrencyRateItem(
    val code: String,
    val name: String,
    val icon: Uri,
    val value: Double,
    val valueString: String,
    val currency: Currency.Code
)

internal enum class ContentState {
    LOADING, ERROR, CONTENT
}

// endregion

internal sealed class RatesEvent {
    data class RatesFetched(val base: Currency.Code, val rates: CurrencyRates) : RatesEvent()
    data class RatesFetchFailed(val error: Throwable) : RatesEvent()

    data class ItemFocused(val item: Currency.Code) : RatesEvent()
    data class ItemUnfocused(val item: Currency.Code) : RatesEvent()
    data class ItemValueChanged(val item: Currency.Code, val value: String) : RatesEvent()

    object RetryFetchRequested : RatesEvent()
}

internal sealed class RatesEffect {
    data class FetchRates(val base: Currency.Code) : RatesEffect()
    object HideKeyboard : RatesEffect()
    object ShowKeyboard : RatesEffect()
}