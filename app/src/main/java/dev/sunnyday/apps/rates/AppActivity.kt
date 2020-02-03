package dev.sunnyday.apps.rates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dev.sunnyday.apps.rates.core.ui.activity.BaseActivity
import dev.sunnyday.apps.rates.core.ui.fragment.add
import dev.sunnyday.apps.rates.databinding.MainActivityBinding
import dev.sunnyday.apps.rates.di.AppFragmentFactory
import dev.sunnyday.apps.rates.ui.rates.RatesFragment
import javax.inject.Inject

internal class AppActivity : BaseActivity(),
    HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    internal lateinit var fragmentFactory: AppFragmentFactory
    
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.container, RatesFragment.name())
            }
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}