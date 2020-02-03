package dev.sunnyday.apps.rates.core.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.sunnyday.apps.rates.core.ui.R
import dev.sunnyday.apps.rates.core.ui.util.resolveColorAttribute

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.statusBarColor = theme.resolveColorAttribute(R.attr.colorPrimaryDark)
        }
        super.onCreate(savedInstanceState)
    }

}