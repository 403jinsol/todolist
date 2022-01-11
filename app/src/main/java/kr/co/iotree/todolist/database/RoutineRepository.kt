package kr.co.iotree.todolist.database

import android.content.Context

class RoutineRepository(private val dao: RoutineDao) {
    companion object {
        @Volatile
        private var INSTANCE: RoutineRepository? = null

        fun getInstance(context: Context): RoutineRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = RoutineRepository(TodoDatabase.getInstance(context).routineDao())
                INSTANCE = instance
                instance
            }
        }
    }

    val readAllTimeAlarm = dao.getAllRoutine()

    suspend fun addRoutine(routine: Routine) {
        dao.insert(routine)
    }

    suspend fun deleteRoutine(routine: Routine) {
        dao.delete(routine)
    }

    fun getRoutine(routineId: Long): Routine {
        return dao.getRoutine(routineId)
    }

    fun getGroupRoutine(groupId: Long): List<Routine> {
        return dao.getGroupRoutine(groupId)
    }

    suspend fun updateStartDate(startDate: Int, routineId: Long) {
        dao.updateStartDate(startDate, routineId)
    }

    suspend fun updateEndDate(endDate: Int, routineId: Long) {
        dao.updateEndDate(endDate, routineId)
    }

    suspend fun updateDay(mon: Boolean, tue: Boolean, wed: Boolean, thu: Boolean, fri: Boolean, sat: Boolean, sun: Boolean, routineId: Long){
        dao.updateDay(mon, tue, wed, thu, fri, sat, sun, routineId)
    }
}