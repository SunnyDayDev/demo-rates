package dev.sunnyday.apps.rates.ui.rates.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sunnyday.apps.rates.core.ui.collection.Differ
import dev.sunnyday.apps.rates.ui.rates.databinding.RatesListItemBinding
import dev.sunnyday.apps.rates.ui.rates.domain.CurrencyRateItem
import javax.inject.Inject

internal class RatesAdapter @Inject constructor(
    private val itemViewHolderFactory: CurrencyRateItemViewHolder.Factory,
    private val differ: Differ<CurrencyRateItem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<CurrencyRateItem> = emptyList()
        set(value) {
            if (value === field) return
            val diff = differ.calculateDiff(field, value)
            field = value
            diff.dispatchUpdatesTo(this)
        }

    init {
        setHasStableIds(true)
    }

    fun forceContentNotTheSameOnNextDiff() {
        differ.forceContentNotTheSameOnNextDiff()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].currency.ordinal.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RatesListItemBinding.inflate(inflater, parent, false)
        return itemViewHolderFactory.create(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is CurrencyRateItemViewHolder -> onBindViewHolder(holder, position)
        else -> Unit
    }

    private fun onBindViewHolder(holder: CurrencyRateItemViewHolder, position: Int) {
        val item = items.getOrNull(position)
            ?: return

        holder.bind(item)
    }

}