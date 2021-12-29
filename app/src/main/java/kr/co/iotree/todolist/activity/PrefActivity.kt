package kr.co.iotree.todolist.activity

import android.app.Application
import kr.co.iotree.todolist.util.PrefUtil

class PrefActivity : Application() {
    override fun onCreate() {
        pref = PrefUtil(applicationContext)
        super.onCreate()
    }

    companion object {
        lateinit var pref: PrefUtil
    }
}