package kr.co.iotree.todolist.activity

import android.app.Application
import android.content.Context
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.PrefUtil

class PrefActivity : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleUtil.getLocalizedContext(base, PrefUtil(base).getPrefString(PrefUtil.LOCALE_CODE, LocaleUtil.OPTION_PHONE_LANGUAGE)))
    }

    override fun onCreate() {
        pref = PrefUtil(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var pref: PrefUtil
    }
}