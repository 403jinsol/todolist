package kr.co.iotree.todolist.util

import android.content.Context
import android.content.SharedPreferences

class PrefUtil(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("todoList", Context.MODE_PRIVATE)

    fun setPrefBool(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getPrefBool(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    companion object{
        const val START_SUNDAY = "startSunday"
    }
}




