package kr.co.iotree.todolist.database

import androidx.lifecycle.LiveData

class Repository(database: TodoDatabase) {
    val groupDao = database.groupDao()
    val todoDao = database.todoDao()
    val readCalendarGroup: LiveData<MutableList<TodoGroup>> = groupDao.getCalenderGroup(false)
    val readProceedGroup: LiveData<MutableList<TodoGroup>> = groupDao.getAllGroup(false)
    val readCompleteGroup: LiveData<MutableList<TodoGroup>> = groupDao.getAllGroup(true)
    val readAllTodo: LiveData<MutableList<Todo>> = todoDao.getAllTodo()
    val readAllStorageTodo: LiveData<MutableList<Todo>> = todoDao.getAllStorageTodo(true)

    companion object {
        private var sInstance: Repository? = null
        fun getInstance(database: TodoDatabase): Repository {
            return sInstance ?: synchronized(this) {
                val instance = Repository(database)
                sInstance = instance
                instance
            }
        }
    }

    suspend fun addGroup(group: TodoGroup) {
        groupDao.insert(group)
    }

    suspend fun updateGroup(groupId: Long, title: String, groupPublic: Int, color: Int, complete: Boolean, reason: Int) {
        groupDao.update(groupId, title, groupPublic, color, complete, reason)
    }

    suspend fun deleteGroup(group: TodoGroup) {
        groupDao.delete(group)
    }

    suspend fun addTodo(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDao.delete(todo)
    }

    suspend fun updateComplete(complete: Boolean, todoId: Long?) {
        todoDao.updateComplete(complete, todoId)
    }

    suspend fun getStorageTodo(groupId: Long, storage: Boolean) {
        todoDao.getGroupStorageTodo(groupId, storage)
    }
}