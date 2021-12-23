package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.TodoGroupRepository
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase

class StorageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoGroupRepository(TodoDatabase.getInstance(application).todoDao(), TodoDatabase.getInstance(application).groupDao())
    val storageGroup = repository.readCalendarGroup
    val storageTodo = repository.readAllStorageTodo

    fun addTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.addTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTodo(todo)
    }

    fun getTodo(todoID: Long): Todo {
        return repository.getTodo(todoID)
    }

    fun getGroupStorageTodo(groupID: Long): MutableList<Todo> {
        return repository.getGroupStorageTodo(groupID)
    }

    fun updateDateTodo(date: Int, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDateTodo(date, todoID)
    }

    fun updateStorageTodo(storage: Boolean, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateStorageTodo(storage, todoID)
    }
}