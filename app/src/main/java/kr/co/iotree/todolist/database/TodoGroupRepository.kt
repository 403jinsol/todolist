package kr.co.iotree.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData

class TodoGroupRepository(val database: TodoDatabase) {
    companion object {
        @Volatile
        private var INSTANCE: TodoGroupRepository? = null

        fun getInstance(context: Context): TodoGroupRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = TodoGroupRepository(TodoDatabase.getInstance(context))
                INSTANCE = instance
                instance
            }
        }
    }

    val groupDao = database.groupDao()
    val todoDao = database.todoDao()

    val readCalendarGroup: LiveData<List<TodoGroup>> = groupDao.getCalenderGroup(false)
    val readProceedGroup: LiveData<List<TodoGroup>> = groupDao.getAllGroup(false)
    val readCompleteGroup: LiveData<List<TodoGroup>> = groupDao.getAllGroup(true)
    val readAllTodo: LiveData<List<Todo>> = todoDao.getAllTodo()
    val readAllStorageTodo: LiveData<List<Todo>> = todoDao.getAllStorageTodo(true)

    fun addGroup(group: TodoGroup) {
        groupDao.insert(group)
    }

    fun updateGroup(groupId: Long, title: String, groupPublic: Int, color: Int, complete: Boolean, reason: Int) {
        groupDao.update(groupId, title, groupPublic, color, complete, reason)
    }

    fun deleteGroup(group: TodoGroup) {
        groupDao.delete(group)
    }

    fun addTodo(todo: Todo) {
        todoDao.insert(todo)
    }

    fun deleteTodo(todo: Todo) {
        todoDao.delete(todo)
    }

    fun getGroup(groupId: Long): TodoGroup {
        return groupDao.getGroup(groupId)
    }

    fun getTodo(todoId: Long): Todo {
        return todoDao.getTodo(todoId)
    }

    fun updateDateTodo(date: Int, todoId: Long) {
        todoDao.updateDate(date, todoId)
    }

    fun updateStorageTodo(storage: Boolean, todoId: Long) {
        todoDao.updateStorage(storage, todoId)
    }

    fun updateContentTodo(content: String, todoId: Long) {
        todoDao.updateContent(content, todoId)
    }

    fun updateComplete(complete: Boolean, todoId: Long) {
        todoDao.updateComplete(complete, todoId)
    }

    fun getAllDayTodo(date: Int): List<Todo> {
        return todoDao.getAllDayTodo(date)
    }

    fun getAllDayCompleteTodo(date: Int, complete: Boolean): List<Todo> {
        return todoDao.getCompleteTodo(date, complete)
    }

    fun getGroupStorageTodo(groupId: Long): List<Todo> {
        return todoDao.getGroupStorageTodo(groupId, true)
    }
}