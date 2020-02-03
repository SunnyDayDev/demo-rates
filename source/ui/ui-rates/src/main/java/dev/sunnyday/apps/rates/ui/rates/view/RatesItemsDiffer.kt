package dev.sunnyday.apps.rates.ui.rates.view

import dev.sunnyday.apps.rates.core.ui.collection.SimpleDiffer
import dev.sunnyday.apps.rates.ui.rates.domain.CurrencyRateItem
import javax.inject.Inject

internal class RatesItemsDiffer @Inject constructor() : SimpleDiffer<CurrencyRateItem>() {

    override fun areItemsTheSame(old: CurrencyRateItem, new: CurrencyRateItem): Boolean =
        old.currency == new.currency

    override fun areContentsTheSame(old: CurrencyRateItem, new: CurrencyRateItem): Boolean =
        old == new

}