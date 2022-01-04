package kr.co.iotree.todolist.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*


class LocaleHelper {
    fun updateResources(context: Context, language: String): Context? {
        var tempContext: Context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res: Resources = tempContext.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        tempContext = tempContext.createConfigurationContext(config)
        return tempContext
    }
}