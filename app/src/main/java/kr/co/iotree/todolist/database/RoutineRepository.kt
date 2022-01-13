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

    val readAllRoutine = dao.getAllRoutine()

    suspend fun addRoutine(routine: Routine) {
        dao.insert(routine)
    }

    suspend fun deleteRoutine(routine: Routine) {
        dao.delete(routine)
    }

    fun getRoutine(routineId: Long): Routine {
        return dao.getRoutine(routineId)
    }

    fun getGroupMondayRoutine(groupId: Long, date: Int, monday: Boolean): List<Routine> {
        return dao.getGroupMondayRoutine(groupId, date, monday)
    }

    fun getGroupTuesdayRoutine(groupId: Long, date: Int, tuesday: Boolean): List<Routine> {
        return dao.getGroupTuesdayRoutine(groupId, date, tuesday)
    }

    fun getGroupWednesdayRoutine(groupId: Long, date: Int, wednesday: Boolean): List<Routine> {
        return dao.getGroupWednesdayRoutine(groupId, date, wednesday)
    }

    fun getGroupThursdayRoutine(groupId: Long, date: Int, thursday: Boolean): List<Routine> {
        return dao.getGroupThursdayRoutine(groupId, date, thursday)
    }

    fun getGroupFridayRoutine(groupId: Long, date: Int, friday: Boolean): List<Routine> {
        return dao.getGroupFridayRoutine(groupId, date, friday)
    }

    fun getGroupSaturdayRoutine(groupId: Long, date: Int, saturday: Boolean): List<Routine> {
        return dao.getGroupSaturdayRoutine(groupId, date, saturday)
    }

    fun getGroupSundayRoutine(groupId: Long, date: Int, sunday: Boolean): List<Routine> {
        return dao.getGroupSundayRoutine(groupId, date, sunday)
    }

    fun updateContent(content: String, routineId: Long) {
        dao.updateContent(content, routineId)
    }

    suspend fun updateStartDate(startDate: Int, routineId: Long) {
        dao.updateStartDate(startDate, routineId)
    }

    suspend fun updateEndDate(endDate: Int, routineId: Long) {
        dao.updateEndDate(endDate, routineId)
    }

    suspend fun updateDay(mon: Boolean, tue: Boolean, wed: Boolean, thu: Boolean, fri: Boolean, sat: Boolean, sun: Boolean, routineId: Long) {
        dao.updateDay(mon, tue, wed, thu, fri, sat, sun, routineId)
    }

    fun getAllGroupRoutine(groupId: Long): List<Routine> {
        return dao.getAllGroupRoutine(groupId)
    }
}