package kr.co.iotree.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData

class TodoRepository(private val dao: TodoDao) {
    companion object {
        @Volatile
        private var INSTANCE: TodoRepository? = null

        fun getInstance(context: Context): TodoRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = TodoRepository(TodoDatabase.getInstance(context).todoDao())
                INSTANCE = instance
                instance
            }
        }
    }

    val readAllTodo: LiveData<List<Todo>> = dao.getAllTodo()
    val readAllStorageTodo: LiveData<List<Todo>> = dao.getAllStorageTodo(true)

    fun addTodo(todo: Todo) {
        dao.insert(todo)
    }

    fun deleteTodo(todo: Todo) {
        dao.delete(todo)
    }

    fun getTodo(todoId: Long): Todo {
        return dao.getTodo(todoId)
    }

    fun updateDateTodo(date: Int, todoId: Long) {
        dao.updateDate(date, todoId)
    }

    fun updateStorageTodo(storage: Boolean, todoId: Long) {
        dao.updateStorage(storage, todoId)
    }

    fun updateContentTodo(content: String, todoId: Long) {
        dao.updateContent(content, todoId)
    }

    fun updateComplete(complete: Boolean, todoId: Long) {
        dao.updateComplete(complete, todoId)
    }

    fun getCalendarTodo(groupId: Long, date: Int, storage: Boolean): List<Todo> {
        return dao.getCalendarTodo(groupId, date, storage)
    }

    fun getAllDayTodo(date: Int): List<Todo> {
        return dao.getAllDayTodo(date)
    }

    fun getAllDayCompleteTodo(date: Int, complete: Boolean): List<Todo> {
        return dao.getCompleteTodo(date, complete)
    }

    fun getGroupStorageTodo(groupId: Long): List<Todo> {
        return dao.getGroupStorageTodo(groupId, true)
    }

    fun getAllCompleteTodo(startDate: Int, endDate: Int, complete: Boolean): List<Todo> {
        return dao.getAllCompleteTodo(startDate, endDate, complete)
    }
}