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
        const val OPTION_PHONE_LANGUAGE = "System"

        /**
         * PrefApplication에서 실행
         * prefLocaleCode: pref에 저장된 locale 코드
         * config 적용된 새 context 반환
         */
        fun getLocalizedContext(baseContext: Context, prefLocaleCode: String): Context {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration) // config에서 locale 가져옴
            Locale.setDefault(currentLocale)
            return if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) { // 기존 locale과 현재 locale이 다르면
                val config = getLocalizedConfiguration(currentLocale) //locale 설정 후 반환된 config
                baseContext.createConfigurationContext(config) // config와 일치하도록 새 context 반환
                baseContext
            } else {
                baseContext
            }
        }

        /**
         * activity에서 locale 변경했을 때 실행
         */
        fun applyLocalizedContext(baseContext: Context, prefLocaleCode: String) {
            val currentLocale = getLocaleFromPrefCode(prefLocaleCode) //바꿀 로케일
            val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration) //기존 로케일
            Locale.setDefault(currentLocale) //기본 로케일 설정
            if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) { // ignoreCase: 대소문자 무시
                val config = getLocalizedConfiguration(currentLocale)
                baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics) // 기존 로케일과 바꿀 로케일이 다르면 업데이트
            }
        }

        /**
         * prefCode가 system 언어면 system locale 반환
         * 그렇지 않으면 입력한 코드에 해당하는 locale 반환
         */
        private fun getLocaleFromPrefCode(prefCode: String): Locale {
            val localeCode = if (prefCode != OPTION_PHONE_LANGUAGE) {
                prefCode //기본 설정값이 아니면 prefCode 그대로
            } else {
                ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language //시스템 로케일 가져옴
            }
            return Locale(localeCode)
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

        /**
         * string 받음
         * prefLocaleCode에 해당되는 locale이 적용된 config 반환
         */
        fun getLocalizedConfiguration(prefLocaleCode: String): Configuration {
            val locale = getLocaleFromPrefCode(prefLocaleCode)
            return getLocalizedConfiguration(locale)
        }

        /**
         * locale 받음
         * locale config에 설정 후 config 반환
         */
        private fun getLocalizedConfiguration(locale: Locale): Configuration {
            val config = Configuration()
            return config.apply { //locale 설정 후 config 반환
                config.setLayoutDirection(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //O 이상이면 localelist 설정
                    config.setLocale(locale)
                    val localeList = LocaleList(locale)
                    LocaleList.setDefault(localeList)
                    config.setLocales(localeList)
                } else {
                    config.setLocale(locale)
                }
            }
        }
    }
}