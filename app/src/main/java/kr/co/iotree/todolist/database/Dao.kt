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
    fun getTodo(groupId: Long?, date: String): MutableList<Todo>
}

@Dao
interface GroupDao {
    @Insert
    fun insert(group: TodoGroup)

    @Delete
    fun delete(group: TodoGroup)

    @Query("SELECT * FROM TodoGroup")
    fun getAllTodoGroup(): MutableList<TodoGroup>

    @Query("SELECT * FROM TodoGroup WHERE group_id = :groupId")
    fun getGroup(groupId: Long?): TodoGroup

    @Query("UPDATE TodoGroup SET title = :title, color = :color WHERE group_id = :groupId")
    fun update(groupId: Long?, title: String, color: String)
}