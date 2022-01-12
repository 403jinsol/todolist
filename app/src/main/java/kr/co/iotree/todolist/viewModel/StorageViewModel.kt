package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.GroupRepository
import kr.co.iotree.todolist.database.TodoRepository
import kr.co.iotree.todolist.database.Todo

class StorageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoRepository.getInstance(application)
    val storageGroup = GroupRepository.getInstance(application).readCalendarGroup
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

    fun getGroupStorageTodo(groupID: Long): List<Todo> {
        return repository.getGroupStorageTodo(groupID)
    }

    fun updateDateTodo(date: Int, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDateTodo(date, todoID)
    }

    fun updateStorageTodo(storage: Boolean, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateStorageTodo(storage, todoID)
    }
}