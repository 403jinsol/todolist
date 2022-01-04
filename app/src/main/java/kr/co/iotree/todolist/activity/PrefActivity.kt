package kr.co.iotree.todolist.activity

import android.app.Application
import android.content.Context
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.PrefUtil
import kr.co.iotree.todolist.util.Storage

class PrefActivity : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleUtil.getLocalizedContext(base, Storage(base).getPreferredLocale()))
    }

    override fun onCreate() {
        pref = PrefUtil(applicationContext)
        storage = Storage(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var pref: PrefUtil
        lateinit var storage: Storage
    }
}