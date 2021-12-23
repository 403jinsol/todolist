package kr.co.iotree.todolist.database

class TimeRepository(private val timeAlarmDao: TimeAlarmDao) {
    val readAllTimeAlarm = timeAlarmDao.getAllTimeAlarm()

    suspend fun addTimeAlarm(timeAlarm: TimeAlarm) {
        timeAlarmDao.insert(timeAlarm)
    }

    suspend fun deleteTimeAlarm(timeAlarm: TimeAlarm) {
        timeAlarmDao.delete(timeAlarm)
    }
}