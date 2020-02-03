package dev.sunnyday.apps.rates.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.sunnyday.apps.rates.core.di.viewModel.InjectableViewModelFactory
import dev.sunnyday.apps.rates.core.ui.fragment.FragmentName
import dev.sunnyday.apps.rates.ui.rates.databinding.RatesFragmentBinding
import dev.sunnyday.apps.rates.ui.rates.effectHandlers.RatesViewEffectHandler
import dev.sunnyday.apps.rates.ui.rates.loop.RatesLoopController
import dev.sunnyday.apps.rates.ui.rates.loop.RatesLoopViewModel
import dev.sunnyday.apps.rates.ui.rates.view.RatesView
import javax.inject.Inject


class RatesFragment @Inject internal constructor(
    private val viewModelFactory: InjectableViewModelFactory,
    private val ratesViewFactory: RatesView.Factory
) : Fragment(), RatesViewEffectHandler.View {

    companion object {

        fun name() = FragmentName.create<RatesFragment>()

    }

    private val loopViewModel: RatesLoopViewModel by lazy {
        val viewModelProvider = ViewModelProvider(this)
        viewModelProvider[RatesLoopViewModel::class.java]
    }

    private val loopController: RatesLoopController
        get() = loopViewModel.loopController

    private val viewEffectHandler: RatesViewEffectHandler
        get() = loopViewModel.viewEffectHandler

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        viewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        RatesFragmentBinding.inflate(inflater, container, false)
            .also(this::onBindingCreated)
            .root

    private fun onBindingCreated(binding: RatesFragmentBinding) {
        val ratesView = ratesViewFactory.create(binding)
        loopController.connect(ratesView)
    }

    override fun onStart() {
        super.onStart()
        viewEffectHandler.view = this
        loopController.start()
    }

    override fun onStop() {
        super.onStop()
        loopController.stop()
        viewEffectHandler.view = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loopController.disconnect()
    }

    override fun hideKeyboard() {
        val view = view ?: return
        val imm: InputMethodManager = context?.getSystemService() ?: return

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showKeyboard() {
        val view = view?.findFocus() ?: return
        val imm: InputMethodManager = context?.getSystemService() ?: return

        imm.showSoftInput(view, 0)
    }

}