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
    fun updateComplete(complete: Boolean, todoId: Long?)

    @Query("UPDATE Todo SET date = 0, storage = :storage WHERE todoId = :todoId")
    fun updateStorage(storage: Boolean, todoId: Long?)

    @Query("UPDATE Todo SET date = :date WHERE todoId = :todoId")
    fun updateDate(date: Int, todoId: Long?)

    @Query("UPDATE Todo SET content = :content WHERE todoId = :todoId")
    fun updateContent(content:String, todoId: Long?)

    @Query("SELECT * FROM Todo WHERE todoId = :todoId")
    fun getTodo(todoId: Long?): Todo

    @Query("SELECT * FROM Todo")
    fun getAllTodo(): LiveData<MutableList<Todo>>

    @Query("SELECT * FROM Todo WHERE group_id = :groupId AND date = :date AND storage = :storage")
    fun getCalendarTodo(groupId: Long?, date: Int, storage: Boolean): MutableList<Todo>

    @Query("SELECT * FROM Todo WHERE group_id = :groupId AND storage = :storage")
    fun getGroupStorageTodo(groupId: Long?, storage: Boolean): MutableList<Todo>

    @Query("SELECT * FROM Todo where date = :date")
    fun getAllDayTodo(date: Int): MutableList<Todo>

    @Query("SELECT * FROM Todo where storage = :storage")
    fun getAllStorageTodo(storage: Boolean): LiveData<MutableList<Todo>>

    @Query("SELECT * FROM Todo WHERE date = :date AND complete = :complete")
    fun getCompleteTodo(date: Int, complete: Boolean): MutableList<Todo>

    @Query("SELECT * FROM Todo WHERE date >= :startDate AND date <= :endDate AND complete = :complete")
    fun getAllCompleteTodo(startDate: Int, endDate: Int, complete: Boolean): MutableList<Todo>
}

@Dao
interface GroupDao {
    @Insert
    fun insert(group: TodoGroup)

    @Delete
    fun delete(group: TodoGroup)

    @Query("UPDATE TodoGroup SET title = :title, groupPublic = :groupPublic, color = :color, complete = :complete, reason = :reason WHERE group_id = :groupId")
    fun update(groupId: Long?, title: String, groupPublic: Int, color: Int, complete: Boolean, reason: Int)

    @Query("SELECT * FROM TodoGroup WHERE group_id = :groupId")
    fun getGroup(groupId: Long?): TodoGroup?

    @Query("SELECT * FROM TodoGroup WHERE complete = :complete")
    fun getAllGroup(complete: Boolean): LiveData<MutableList<TodoGroup>>

    @Query("SELECT * FROM TodoGroup WHERE complete = :complete AND (groupPublic = 3)")
    fun getCalenderGroup(complete: Boolean): LiveData<MutableList<TodoGroup>>
}

@Dao
interface TimeAlarmDao {
    @Insert
    fun insert(timeAlarm: TimeAlarm)

    @Delete
    fun delete(timeAlarm: TimeAlarm)

    @Query("SELECT * FROM TimeAlarm ORDER BY allTime ASC")
    fun getAllTimeAlarm(): LiveData<MutableList<TimeAlarm>>
}