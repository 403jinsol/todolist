package kr.co.iotree.todolist.database

import android.content.Context

class TimeRepository(private val timeAlarmDao: TimeAlarmDao) {
    companion object {
        @Volatile
        private var INSTANCE: TimeRepository? = null

        fun getInstance(context: Context): TimeRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = TimeRepository(TodoDatabase.getInstance(context).timeAlarmDao())
                INSTANCE = instance
                instance
            }
        }
    }

    val readAllTimeAlarm = timeAlarmDao.getAllTimeAlarm()

    fun addTimeAlarm(timeAlarm: TimeAlarm) {
        timeAlarmDao.insert(timeAlarm)
    }

    fun deleteTimeAlarm(timeAlarm: TimeAlarm) {
        timeAlarmDao.delete(timeAlarm)
    }

    fun updateTimeAlarm(timeAlarmId: Long?, allTime: Int, hour: Int, minute: Int){
        timeAlarmDao.updateTimeAlarm(timeAlarmId, allTime, hour, minute)
    }

    fun getTimeAlarm(timeAlarmId: Long?):TimeAlarm?{
        return timeAlarmDao.getTimeAlarm(timeAlarmId)
    }
}