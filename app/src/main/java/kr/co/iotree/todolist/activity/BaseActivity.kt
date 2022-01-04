package kr.co.iotree.todolist.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.Storage

open class BaseActivity : AppCompatActivity() {
    private lateinit var oldPrefLocaleCode: String

    override fun attachBaseContext(newBase: Context) {
        oldPrefLocaleCode = Storage(newBase).getPreferredLocale()
        applyOverrideConfiguration(LocaleUtil.getLocalizedConfiguration(oldPrefLocaleCode))
        super.attachBaseContext(newBase)
    }

    override fun onResume() {
        val currentLocaleCode = Storage(this).getPreferredLocale()
        if (oldPrefLocaleCode != currentLocaleCode) {
            recreate() //로케일이 변경되면 업데이트 위해 액티비티 다시 시작
            oldPrefLocaleCode = currentLocaleCode
        }
        super.onResume()
    }
}