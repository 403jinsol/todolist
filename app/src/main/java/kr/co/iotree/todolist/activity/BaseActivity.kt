package kr.co.iotree.todolist.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.LocaleUtil.Companion.OPTION_PHONE_LANGUAGE
import kr.co.iotree.todolist.util.PrefUtil
import kr.co.iotree.todolist.util.PrefUtil.Companion.LOCALE_CODE

open class BaseActivity : AppCompatActivity() {
    private lateinit var oldPrefLocaleCode: String

    override fun attachBaseContext(newBase: Context) {
        oldPrefLocaleCode = PrefUtil(newBase).getPrefString(LOCALE_CODE, OPTION_PHONE_LANGUAGE)
        applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(oldPrefLocaleCode))
        super.attachBaseContext(newBase)
    }

    override fun onResume() {
        val currentLocaleCode = PrefUtil(this).getPrefString(LOCALE_CODE, OPTION_PHONE_LANGUAGE)
        if (oldPrefLocaleCode != currentLocaleCode) {
            recreate() //로케일이 변경되면 업데이트 위해 액티비티 다시 시작
            oldPrefLocaleCode = currentLocaleCode
        }
        super.onResume()
    }
}