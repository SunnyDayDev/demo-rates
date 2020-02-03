package dev.sunnyday.apps.rates.ui.rates.view

import android.net.Uri
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import dev.sunnyday.apps.rates.domain.currencies.Currency
import dev.sunnyday.apps.rates.ui.rates.databinding.RatesListItemBinding
import dev.sunnyday.apps.rates.ui.rates.domain.CurrencyRateItem
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent.*
import io.reactivex.Observer

internal class CurrencyRateItemViewHolder @AssistedInject constructor(
    @Assisted binding: RatesListItemBinding,
    private val events: Observer<RatesEvent>
) : RecyclerView.ViewHolder(binding.root) {

    private val name = binding.name
    private val code = binding.code
    private val input = binding.input
    private val icon = binding.icon

    private var loadedIcon: Uri? = null

    private var isUpdating = false

    private lateinit var currency: Currency.Code

    init {
        input.setOnFocusChangeListener { _, hasFocus ->
            if (isUpdating) return@setOnFocusChangeListener

            val focusEvent =
                if (hasFocus) ItemFocused(currency)
                else ItemUnfocused(currency)

            events.onNext(focusEvent)
        }

        input.doAfterTextChanged {
            if (isUpdating) return@doAfterTextChanged

            val value = it?.toString() ?: ""
            events.onNext(ItemValueChanged(currency, value))
        }
    }

    fun bind(item: CurrencyRateItem) {
        isUpdating = true
        currency = item.currency

        icon.loadIcon(item.icon)

        if (name.text != item.name) {
            name.text = item.name
        }
        if (code.text != item.code) {
            code.text = item.code
        }
        if (input.text.toString() != item.valueString) {
            input.setText(item.valueString)
        }

        isUpdating = false
    }

    private fun ImageView.loadIcon(icon: Uri) {
        if (loadedIcon == icon) {
            return
        }

        val glide = Glide.with(this)

        glide.clear(this)

        glide.load(icon)
            .into(this)

        loadedIcon = icon
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(binding: RatesListItemBinding): CurrencyRateItemViewHolder

    }

}