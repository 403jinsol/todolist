package kr.co.iotree.todolist.database

import androidx.room.*

@Dao
interface TodoDao {
    @Insert
    fun insert(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("UPDATE Todo SET complete = :complete WHERE id= :id")
    fun updateComplete(complete: Boolean, id: Long?)

    @Query("SELECT * FROM Todo WHERE todoGroupName = :todoGroupName AND date = :date")
    fun getTodo(todoGroupName: String, date: String): MutableList<Todo>

    @Query("DELETE FROM Todo WHERE id = :id")
    fun deleteTodoById(id: Long?)
}