package kr.co.iotree.todolist.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.core.os.ConfigurationCompat
import java.util.*


class LocaleUtil {
    companion object {
        private val supportedLocales = listOf("ko", "en", "ja")
        const val OPTION_PHONE_LANGUAGE = "System"

        /**
         * 기본 설정 값 = 'OPTION_PHONE_LANGUAGE'가 현재 시스템의 로케일을 반환할 때 기본 설정 값에 따라 사용할 로케일을 반환
         * 그렇지 않으면 supportedLocales 로케일 코드 반환
         */
        private fun getLocaleFromPrefCode(prefCode: String): Locale {
            val localeCode = if (prefCode != OPTION_PHONE_LANGUAGE) {
                prefCode
            } else {
                val systemLang = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language //시스템 로케일 가져옴
                if (systemLang in supportedLocales) {
                    systemLang
                } else {
                    "ko" //supportedLocales에도 없다면
                }
            }
            return Locale(localeCode)
        }

        fun getLocalizedConfiguration(prefLocaleCode: String): Configuration {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            return getLocalizedConfiguration(locale)
        }

        private fun getLocalizedConfiguration(locale: Locale): Configuration {
            val config = Configuration()
            return config.apply {
                config.setLayoutDirection(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    config.setLocale(locale)
                    val localeList = LocaleList(locale)
                    LocaleList.setDefault(localeList)
                    config.setLocales(localeList)
                } else {
                    config.setLocale(locale)
                }
            }
        }

        fun getLocalizedContext(baseContext: Context, prefLocaleCode: String): Context {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            return if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.createConfigurationContext(config)
                baseContext
            } else {
                baseContext
            }
        }

        /**
         * activity에서 locale 변경했을 때 실행
         */
        fun applyLocalizedContext(baseContext: Context, prefLocaleCode: String) {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
            Locale.setDefault(currentLocale)
            if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) { // ignoreCase: 대소문자 무시
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics) //다르면 업데이트
            }
        }

        @Suppress("DEPRECATION")
        /**
         * 현재 Configuration의 Locale 반환
         */
        private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                configuration.locales.get(0)
            } else {
                configuration.locale
            }
        }
    }
}