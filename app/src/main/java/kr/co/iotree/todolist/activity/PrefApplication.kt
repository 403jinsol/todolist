package kr.co.iotree.todolist.activity

import android.app.Application
import android.content.Context
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.PrefUtil

class PrefApplication : Application() {
    override fun attachBaseContext(base: Context) { //기본 context 설정
        pref = PrefUtil(base)
        super.attachBaseContext(LocaleUtil.getLocalizedContext(base, pref.getPrefString(PrefUtil.LOCALE_CODE, LocaleUtil.OPTION_PHONE_LANGUAGE)))
    }

    companion object {
        lateinit var pref: PrefUtil
    }
}