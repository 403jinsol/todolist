package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.Repository
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.util.getToday

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(TodoDatabase.getInstance(application, viewModelScope))

    val allCalendarGroup: LiveData<MutableList<TodoGroup>> = repository.readCalendarGroup
    val allTodo: LiveData<MutableList<Todo>> = repository.readAllTodo

    var year = MutableLiveData<Int>().also {
        it.value = getToday("yyyy")
    }

    var month = MutableLiveData<Int>().also {
        it.value = getToday("MM")
    }

    val date = MutableLiveData<Int>().also {
        it.value = getToday("d")
    }

    val isMonth = MutableLiveData<Boolean>().also {
        it.value = true
    }

    val completeCount = MutableLiveData<Int>().also {
        it.value = repository.todoDao.getAllCompleteTodo("${year.value!!}${month.value!!}1".toInt(), "${year.value!!}${month.value!!}31".toInt(), true).size
    }

    fun changeCompleteCount(year: Int, month: Int) {
        completeCount.value = repository.todoDao.getAllCompleteTodo("$year${month}1".toInt(), "$year${month}31".toInt(), true).size
    }

    fun updateComplete(complete: Boolean, todoId: Long?) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateComplete(complete, todoId)
    }

    fun addTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.addTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTodo(todo)
    }
}