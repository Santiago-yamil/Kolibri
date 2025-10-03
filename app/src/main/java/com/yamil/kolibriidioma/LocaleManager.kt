package com.yamil.kolibriidioma


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import java.util.Locale

object LocaleManager {
    private const val PREFS = "app_prefs"
    private const val KEY_LANG = "lang"

    fun persistLanguage(context: Context, lang: String) {
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .edit().putString(KEY_LANG, lang).apply()
    }

    fun currentLanguage(context: Context): String {
        return context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .getString(KEY_LANG, "es") ?: "es"
    }

    fun wrap(base: Context): Context {
        val lang = currentLanguage(base)
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val res = base.resources
        val config = res.configuration

        // Ajusta el locale de la config
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            config.setLayoutDirection(locale)
            return base.createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            @Suppress("DEPRECATION")
            res.updateConfiguration(config, res.displayMetrics)
            return base
        }
    }
}
