package kr.co.iotree.todolist.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.BaseApplication.Companion.pref
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.LocaleUtil.Companion.OPTION_PHONE_LANGUAGE
import kr.co.iotree.todolist.util.PrefUtil.Companion.APP_THEME
import kr.co.iotree.todolist.util.PrefUtil.Companion.LOCALE_CODE
import kr.co.iotree.todolist.util.PrefUtil.Companion.SYSTEM_THEME

open class BaseActivity : AppCompatActivity() {
    private lateinit var oldPrefLocaleCode: String
    private lateinit var oldPrefTheme: String

    override fun attachBaseContext(newBase: Context) {
        oldPrefLocaleCode = pref.getPrefString(LOCALE_CODE, OPTION_PHONE_LANGUAGE)
        oldPrefTheme = pref.getPrefString(APP_THEME, SYSTEM_THEME)

        applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(oldPrefLocaleCode)) //config 재정의를 위해 호출
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (pref.getPrefString(APP_THEME, SYSTEM_THEME)) {
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setTheme(R.style.Theme_TodoList_Light)
            }
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                setTheme(R.style.Theme_TodoList_Dark)
            }
        }
    }

    override fun onResume() {
        val currentLocaleCode = pref.getPrefString(LOCALE_CODE, OPTION_PHONE_LANGUAGE)
        val currentTheme = pref.getPrefString(APP_THEME, SYSTEM_THEME)

        if (oldPrefLocaleCode != currentLocaleCode || oldPrefTheme != currentTheme) {
            recreate() //로케일이 변경되면 업데이트 위해 액티비티 다시 시작
            oldPrefLocaleCode = currentLocaleCode
            oldPrefTheme = currentTheme
        }
        super.onResume()
    }
}