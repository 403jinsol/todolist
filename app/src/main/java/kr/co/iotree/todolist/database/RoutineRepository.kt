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

    fun addRoutine(routine: Routine) {
        dao.insert(routine)
    }

    fun deleteRoutine(routine: Routine) {
        dao.delete(routine)
    }

    fun getRoutine(routineId: Long): Routine {
        return dao.getRoutine(routineId)
    }

    fun getGroupRoutine(groupId: Long): MutableList<Routine> {
        return dao.getGroupRoutine(groupId)
    }

    fun updateStartDate(startDate: Int, routineId: Long) {
        dao.updateStartDate(startDate, routineId)
    }

    fun updateEndDate(endDate: Int, routineId: Long) {
        dao.updateEndDate(endDate, routineId)
    }
}