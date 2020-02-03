package dev.sunnyday.apps.rates.ui.rates.view

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx2.RxConnectables
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import dev.sunnyday.apps.rates.core.common.weak
import dev.sunnyday.apps.rates.core.ui.util.Dimen
import dev.sunnyday.apps.rates.ui.rates.databinding.RatesFragmentBinding
import dev.sunnyday.apps.rates.ui.rates.domain.ContentState
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent
import dev.sunnyday.apps.rates.ui.rates.domain.RatesEvent.*
import dev.sunnyday.apps.rates.ui.rates.domain.RatesModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlin.math.min

internal class RatesView @AssistedInject constructor(
    @Assisted private val binding: RatesFragmentBinding,
    private val externalEventsSource: Observable<RatesEvent>,
    private val itemsAdapter: RatesAdapter
) : Connectable<RatesModel, RatesEvent> {

    private var previousModel: RatesModel? by weak()

    init {
        setupItemsList()
        setupAppBarElevationTracking()
    }

    private fun setupItemsList() {
        val list = binding.list

        list.adapter = itemsAdapter
        list.itemAnimator = DefaultItemAnimator().apply {
            supportsChangeAnimations = false
        }
    }

    private fun setupAppBarElevationTracking() {
        val list = binding.list
        val appBar = binding.appBar

        val appbarElevation = Dimen.dp(4, list.context)
        val maxOffset = Dimen.dp(16, list.context)

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val offset = min(recyclerView.computeVerticalScrollOffset().toFloat(), maxOffset)
                val offsetRatio = offset / maxOffset
                appBar.elevation = offsetRatio * appbarElevation
            }

        })
    }

    override fun connect(output: Consumer<RatesEvent>): Connection<RatesModel> {
        val connectable: Connectable<RatesModel, RatesEvent> = RxConnectables.fromTransformer {
            val disposable = it.subscribeBy(onNext = this::render)

            Observable.merge(getEventsSource(), externalEventsSource)
                .doFinally { disposable.dispose() }
        }

        return connectable.connect(output)
    }

    private fun getEventsSource(): Observable<RatesEvent> =
        binding.retryButton.clicks().map { RetryFetchRequested }

    private fun render(model: RatesModel) {
        if (model === previousModel) {
            itemsAdapter.forceContentNotTheSameOnNextDiff()
        }

        itemsAdapter.items = model.items

        binding.list.isVisible = model.contentState == ContentState.CONTENT
        binding.loadingContent.isVisible = model.contentState == ContentState.LOADING
        binding.errorContent.isVisible = model.contentState == ContentState.ERROR
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(binding: RatesFragmentBinding): RatesView

    }

}