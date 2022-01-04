package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.TodoGroupRepository
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.util.getToday

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoGroupRepository.getInstance(application)

    val allCalendarGroup: LiveData<MutableList<TodoGroup>> = repository.readCalendarGroup
    val allTodo: LiveData<MutableList<Todo>> = repository.readAllTodo

    fun sfas():Int{
        return repository.hashCode()
    }

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

    val groupTodo = MutableLiveData<MutableList<Todo>>()

    fun getGroupTodo(groupId: Long?) {
        groupTodo.value = repository.todoDao.getCalendarTodo(groupId, "${year.value!!}${month.value!!}${date.value}".toInt(), false)
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

    fun getAllDayTodo(date: Int): MutableList<Todo> {
        return repository.getAllDayTodo(date)
    }

    fun getAllDayCompleteTodo(date: Int): MutableList<Todo> {
        return repository.getAllDayCompleteTodo(date)
    }

    fun getGroup(groupId: Long): TodoGroup {
        return repository.getGroup(groupId)
    }

    fun getTodo(todoID: Long): Todo {
        return repository.getTodo(todoID)
    }

    fun updateDateTodo(date: Int, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDateTodo(date, todoID)
    }

    fun updateStorageTodo(storage: Boolean, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateStorageTodo(storage, todoID)
    }

    fun updateContentTodo(content: String, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateContentTodo(content, todoID)
    }

}