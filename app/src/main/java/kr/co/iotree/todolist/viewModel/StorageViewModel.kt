package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.Repository
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup

class StorageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(TodoDatabase.getInstance(application).todoDao(), TodoDatabase.getInstance(application).groupDao())
    val storageGroup = repository.readCalendarGroup
    val storageTodo = repository.readAllStorageTodo

    fun addTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.addTodo(todo)
    }

    fun getGroupStorageTodo(groupID: Long): MutableList<Todo> {
        return repository.getGroupStorageTodo(groupID)
    }
}