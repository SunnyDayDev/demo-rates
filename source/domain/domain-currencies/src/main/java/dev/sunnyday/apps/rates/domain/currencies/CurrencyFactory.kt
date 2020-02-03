package dev.sunnyday.apps.rates.domain.currencies

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import timber.log.Timber
import java.util.*
import javax.inject.Inject


internal class CurrencyFactory @Inject constructor(
    private val context: Context
) {

    private val cache = mutableMapOf<Currency.Code, Currency>()

    fun create(code: Currency.Code): Currency =
        cache.getOrPut(code) {
            Currency(
                code = code,
                icon = getIcon(code),
                name = getName(code)
            )
        }

    private fun getIcon(code: Currency.Code): Uri {
        val name = getResName("ic", code)

        val id = getResIdentifier(name, "drawable")
            ?: R.drawable.currencies__ic__stub
                .also { Timber.e(Error("Currency doesn't have an icon: $code")) }

        val resources: Resources = context.resources

        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(id))
            .appendPath(resources.getResourceTypeName(id))
            .appendPath(resources.getResourceEntryName(id))
            .build()
    }

    private fun getName(code: Currency.Code): String {
        val name = getResName("prompt", code)

        val id = getResIdentifier(name, "string")
            ?: R.string.currencies__prompt__stub
                .also { Timber.e(Error("Currency doesn't have a name: $code")) }

        return context.getString(id)
    }

    private fun getResIdentifier(name: String, defType: String): Int? =
        context.resources.getIdentifier(name, defType, context.packageName)
            .takeIf { it != 0 }

    private fun getResName(resType: String, code: Currency.Code): String =
        "currencies__${resType}__${code.name.toLowerCase(Locale.ROOT)}"

}