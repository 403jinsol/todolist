package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.*
import kr.co.iotree.todolist.util.getToday

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepository = TodoGroupRepository.getInstance(application)
    private val timeRepository = TimeRepository.getInstance(application)

    val allCalendarGroup: LiveData<MutableList<TodoGroup>> = todoRepository.readCalendarGroup
    val allTodo: LiveData<MutableList<Todo>> = todoRepository.readAllTodo
    val groupTodo = MutableLiveData<MutableList<Todo>>()
    val allTime: LiveData<MutableList<TimeAlarm>> = timeRepository.readAllTimeAlarm

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
        it.value = todoRepository.todoDao.getAllCompleteTodo(String.format("%04d%02d01", year.value, month.value).toInt(), String.format("%04d%02d31", year.value, month.value).toInt(), true).size
    }

    fun getGroup(groupId: Long): TodoGroup {
        return todoRepository.getGroup(groupId)
    }

    fun getTodo(todoID: Long): Todo {
        return todoRepository.getTodo(todoID)
    }

    fun getGroupTodo(groupId: Long?) {
        groupTodo.value = todoRepository.todoDao.getCalendarTodo(groupId, String.format("%04d%02d%02d", year.value, month.value, date.value).toInt(), false)
    }

    fun changeCompleteCount(year: Int, month: Int) {
        completeCount.value = todoRepository.todoDao.getAllCompleteTodo(String.format("%04d%02d1", year, month).toInt(), String.format("%04d%02d31", year, month).toInt(), true).size
    }

    fun updateComplete(complete: Boolean, todoId: Long?) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.updateComplete(complete, todoId)
    }

    fun addTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.addTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.deleteTodo(todo)
    }

    fun getAllDayTodo(date: Int): MutableList<Todo> {
        return todoRepository.getAllDayTodo(date)
    }

    fun getAllDayCompleteTodo(date: Int, complete: Boolean): MutableList<Todo> {
        return todoRepository.getAllDayCompleteTodo(date, complete)
    }

    // dialog 기능에서 사용
    fun updateContentTodo(content: String, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.updateContentTodo(content, todoID)
    }

    fun updateDateTodo(date: Int, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.updateDateTodo(date, todoID)
    }

    fun updateStorageTodo(storage: Boolean, todoID: Long) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.updateStorageTodo(storage, todoID)
    }

}