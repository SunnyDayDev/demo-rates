package dev.sunnyday.apps.rates.ui.rates.domain

import com.spotify.mobius.First.first
import com.spotify.mobius.Next.*
import dev.sunnyday.apps.rates.core.common.collection.mutated
import dev.sunnyday.apps.rates.domain.currencies.Currency
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent.*
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEffect.*
import javax.inject.Inject
import kotlin.math.min

internal class RatesLogic @Inject constructor() : RatesUpdate, RatesInit {

    override fun init(model: RatesModel): RatesFirst = first(
        model,
        setOf(FetchRates(model.base))
    )

    override fun update(model: RatesModel, event: RatesEvent): RatesNext = when (event) {
        is RatesFetched -> onRatesFetched(model, event)
        is RatesFetchFailed -> onRatesFetchFailed(model)

        is RetryFetchRequested -> onRetryFetchRequested(model)

        is ItemFocused -> onItemFocused(model, event)
        is ItemUnfocused -> onItemUnfocused(model, event)
        is ItemValueChanged -> onItemValueChanged(model, event)
    }

    private fun onRatesFetched(model: RatesModel, event: RatesFetched): RatesNext {
        if (model.base != event.base)
            return noChange()

        val nextRates = event.rates.rates

        val baseRate = nextRates[model.base] ?: 1.0
        val baseValue = model.baseValue

        val nextCurrenciesCodes = nextRates.keys

        val previousItems = model.items
            .filter { nextCurrenciesCodes.contains(it.currency) }
            .associateBy { it.currency }

        val previousCurrenciesCodes = previousItems.keys

        val nextItemsCodes = setOf(model.base) + previousCurrenciesCodes + nextCurrenciesCodes

        val nextItems = nextItemsCodes.map { code ->
            val currency = event.rates.getCurrency(code)
            val value = getValue(baseValue, baseRate, nextRates.getValue(code))
            val valueString = previousItems[code]
                ?.takeIf { it.value == value }
                ?.valueString
                ?: formatValue(value)

            getCurrencyRateItem(currency, value, valueString)
        }

        return next(model.copy(
            rates = nextRates,
            items = nextItems.toList(),
            contentState = ContentState.CONTENT
        ))
    }

    private fun onRatesFetchFailed(model: RatesModel): RatesNext =
        next(model.copy(contentState = ContentState.ERROR))

    private fun onRetryFetchRequested(model: RatesModel): RatesNext =
        next(
            model.copy(contentState = ContentState.LOADING),
            setOf(FetchRates(model.base))
        )

    private fun onItemFocused(model: RatesModel, event: ItemFocused): RatesNext {
        val nextBase = event.item

        if (model.base == nextBase) {
            return dispatch(setOf(ShowKeyboard))
        }

        val nextBaseItem = model.items
            .find { it.currency == nextBase }
            ?: return noChange()

        val nextItems = model.items.mutated {
            remove(nextBaseItem)
            add(0, nextBaseItem)
        }

        return next(
            model.copy(
                base = nextBase,
                items = nextItems
            ),
            setOf(FetchRates(nextBase), ShowKeyboard)
        )
    }

    private fun onItemUnfocused(model: RatesModel, event: ItemUnfocused): RatesNext =
        if (model.base == event.item) {
            dispatch(setOf(HideKeyboard))
        } else {
            noChange()
        }

    private fun onItemValueChanged(model: RatesModel, event: ItemValueChanged): RatesNext {
        val changedItemCurrency = event.item

        if (changedItemCurrency != model.base) {
            // return previous model to update UI to previous state
            return next(model)
        }

        val nextBaseValue = event.value.toDoubleOrNull() ?: 0.0

        val baseRate = model.rates.getValue(changedItemCurrency)

        val baseItem = model.baseItem
            ?: return next(model)

        val nextBaseItem = baseItem.copy(
            value = nextBaseValue,
            valueString = event.value
        )

        val nextItems = model.items.map {
            if (it === baseItem) {
                nextBaseItem
            } else {
                val rate = model.rates.getValue(it.currency)
                val nextValue = getValue(nextBaseValue, baseRate, rate)

                it.copy(
                    value = nextValue,
                    valueString = formatValue(nextValue)
                )
            }
        }

        return next(model.copy(
            items = nextItems
        ))
    }

    private fun getValue(baseValue: Double, baseRate: Double, rate: Double): Double =
        baseValue / baseRate * rate

    private fun formatValue(value: Double): String {
        if (value == 0.0)
            return ""

        val maxDecimalLength = 2
        val full = String.format("%.${maxDecimalLength}f", value)
        val zerosSize = full.reversed().indexOfFirst { it != '0' }
        val cutSize = min(zerosSize, maxDecimalLength - 2)
        return full.substring(0, full.length - cutSize)
    }

    private fun getCurrencyRateItem(
        currency: Currency,
        value: Double, valueString: String
    ): CurrencyRateItem =
        CurrencyRateItem(
            code = currency.code.name,
            name = currency.name,
            icon = currency.icon,
            value = value,
            valueString = valueString,
            currency = currency.code
        )

    private val RatesModel.baseValue: Double
        get() = baseItem?.value ?: 0.0

    private val RatesModel.baseItem: CurrencyRateItem?
        get() = items.find { it.currency == base }

}