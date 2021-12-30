package kr.co.iotree.todolist.util

import android.content.Context
import android.content.SharedPreferences

class PrefUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("todoList", Context.MODE_PRIVATE)

    fun setPrefBool(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getPrefBool(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    fun setPrefString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getPrefString(key: String, defValue: String): String? {
        return prefs.getString(key, defValue)
    }

    companion object {
        const val START_SUNDAY = "startSunday"
        const val ORDER_COMPLETE = "startSunday"
        const val MODE_MONTH = "modeMonth"
        const val LOCALE = "locale"
    }
}




