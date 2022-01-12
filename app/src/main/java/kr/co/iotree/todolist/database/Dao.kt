package kr.co.iotree.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert
    fun insert(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("UPDATE Todo SET complete = :complete WHERE todoId = :todoId")
    fun updateComplete(complete: Boolean, todoId: Long)

    @Query("UPDATE Todo SET storage = :storage WHERE todoId = :todoId")
    fun updateStorage(storage: Boolean, todoId: Long)

    @Query("UPDATE Todo SET date = :date WHERE todoId = :todoId")
    fun updateDate(date: Int, todoId: Long?)

    @Query("UPDATE Todo SET content = :content WHERE todoId = :todoId")
    fun updateContent(content: String, todoId: Long)

    @Query("SELECT * FROM Todo WHERE todoId = :todoId")
    fun getTodo(todoId: Long): Todo

    @Query("SELECT * FROM Todo")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo WHERE group_id = :groupId AND date = :date AND storage = :storage")
    fun getCalendarTodo(groupId: Long, date: Int, storage: Boolean): List<Todo>

    @Query("SELECT * FROM Todo WHERE group_id = :groupId AND storage = :storage")
    fun getGroupStorageTodo(groupId: Long, storage: Boolean): List<Todo>

    @Query("SELECT * FROM Todo where date = :date")
    fun getAllDayTodo(date: Int): List<Todo>

    @Query("SELECT * FROM Todo where storage = :storage")
    fun getAllStorageTodo(storage: Boolean): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo WHERE date = :date AND complete = :complete")
    fun getCompleteTodo(date: Int, complete: Boolean): List<Todo>

    @Query("SELECT * FROM Todo WHERE date >= :startDate AND date <= :endDate AND complete = :complete")
    fun getAllCompleteTodo(startDate: Int, endDate: Int, complete: Boolean): List<Todo>
}

@Dao
interface GroupDao {
    @Insert
    fun insert(group: TodoGroup)

    @Delete
    fun delete(group: TodoGroup)

    @Query("UPDATE TodoGroup SET title = :title, groupPublic = :groupPublic, color = :color, complete = :complete, reason = :reason WHERE group_id = :groupId")
    fun update(groupId: Long, title: String, groupPublic: Int, color: Int, complete: Boolean, reason: Int)

    @Query("SELECT * FROM TodoGroup WHERE group_id = :groupId")
    fun getGroup(groupId: Long): TodoGroup

    @Query("SELECT * FROM TodoGroup WHERE complete = :complete")
    fun getAllGroup(complete: Boolean): LiveData<List<TodoGroup>>

    @Query("SELECT * FROM TodoGroup WHERE complete = :complete AND (groupPublic = 3)")
    fun getCalenderGroup(complete: Boolean): LiveData<List<TodoGroup>>
}

@Dao
interface TimeAlarmDao {
    @Insert
    fun insert(timeAlarm: TimeAlarm)

    @Delete
    fun delete(timeAlarm: TimeAlarm)

    @Query("SELECT * FROM TimeAlarm WHERE timeAlarmId = :timeAlarmId")
    fun getTimeAlarm(timeAlarmId: Long): TimeAlarm

    @Query("SELECT * FROM TimeAlarm ORDER BY allTime ASC")
    fun getAllTimeAlarm(): LiveData<List<TimeAlarm>>
}

@Dao
interface RoutineDao {
    @Insert
    fun insert(routine: Routine)

    @Delete
    fun delete(routine: Routine)

    @Query("SELECT * FROM Routine")
    fun getAllRoutine(): LiveData<List<Routine>>

    @Query("SELECT * FROM Routine WHERE routineId = :routineId")
    fun getRoutine(routineId: Long): Routine

    @Query("SELECT * FROM Routine WHERE group_id = :groupId")
    fun getGroupRoutine(groupId: Long): List<Routine>

    @Query("SELECT * FROM Routine WHERE startDate <= :date AND endDate >= :date AND monday = :monday AND group_id = :groupId")
    fun getGroupMondayRoutine(groupId: Long, date: Int, monday: Boolean): List<Routine>

    @Query("SELECT * FROM Routine WHERE startDate <= :date AND endDate >= :date AND tuesday = :tuesday AND group_id = :groupId")
    fun getGroupTuesdayRoutine(groupId: Long, date: Int, tuesday: Boolean): List<Routine>

    @Query("SELECT * FROM Routine WHERE startDate <= :date AND endDate >= :date AND wednesday = :wednesday AND group_id = :groupId")
    fun getGroupWednesdayRoutine(groupId: Long, date: Int, wednesday: Boolean): List<Routine>

    @Query("SELECT * FROM Routine WHERE startDate <= :date AND endDate >= :date AND thursday = :thursday AND group_id = :groupId")
    fun getGroupThursdayRoutine(groupId: Long, date: Int, thursday: Boolean): List<Routine>

    @Query("SELECT * FROM Routine WHERE startDate <= :date AND endDate >= :date AND friday = :friday AND group_id = :groupId")
    fun getGroupFridayRoutine(groupId: Long, date: Int, friday: Boolean): List<Routine>

    @Query("SELECT * FROM Routine WHERE startDate <= :date AND endDate >= :date AND saturday = :saturday AND group_id = :groupId")
    fun getGroupSaturdayRoutine(groupId: Long, date: Int, saturday: Boolean): List<Routine>

    @Query("SELECT * FROM Routine WHERE startDate <= :date AND endDate >= :date AND sunday = :sunday AND group_id = :groupId")
    fun getGroupSundayRoutine(groupId: Long, date: Int, sunday: Boolean): List<Routine>

    @Query("SELECT * FROM Routine WHERE group_id = :groupId")
    fun getAllGroupRoutine(groupId: Long): List<Routine>

    @Query("UPDATE routine SET content = :content WHERE routineId = :routineId")
    fun updateContent(content: String, routineId: Long)

    @Query("UPDATE routine SET startDate = :startDate WHERE routineId = :routineId")
    fun updateStartDate(startDate: Int, routineId: Long)

    @Query("UPDATE routine SET endDate = :endDate WHERE routineId = :routineId")
    fun updateEndDate(endDate: Int, routineId: Long)

    @Query("UPDATE routine SET monday = :mon, tuesday=:tue, wednesday=:wed, thursday=:thu, friday=:fri, saturday=:sat, sunday=:sun WHERE routineId = :routineId")
    fun updateDay(mon: Boolean, tue: Boolean, wed: Boolean, thu: Boolean, fri: Boolean, sat: Boolean, sun: Boolean, routineId: Long)
}