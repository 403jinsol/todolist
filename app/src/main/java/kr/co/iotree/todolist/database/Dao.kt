package kr.co.iotree.todolist.database

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

    @Query("SELECT * FROM Todo WHERE group_id = :groupId AND date = :date")
    fun getTodo(groupId: Long?, date: Int): MutableList<Todo>

    @Query("SELECT * FROM Todo WHERE date = :date")
    fun getAllTodo(date: String): MutableList<Todo>

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

    @Query("SELECT * FROM TodoGroup WHERE complete = :complete AND (groupPublic = 3)")
    fun getCalenderGroup(complete: Boolean): MutableList<TodoGroup>

    @Query("SELECT * FROM TodoGroup WHERE complete = :complete AND (groupPublic = 3 OR groupPublic = 4)")
    fun getAllGroup(complete: Boolean): MutableList<TodoGroup>

    @Query("SELECT * FROM TodoGroup WHERE group_id = :groupId")
    fun getGroup(groupId: Long?): TodoGroup

    @Query("UPDATE TodoGroup SET title = :title, groupPublic = :groupPublic, color = :color, complete = :complete, reason = :reason WHERE group_id = :groupId")
    fun update(groupId: Long?, title: String, groupPublic: Int, color: Int, complete: Boolean, reason: Int)
}