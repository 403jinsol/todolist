package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.activity.BaseApplication.Companion.pref
import kr.co.iotree.todolist.database.*
import kr.co.iotree.todolist.util.PrefUtil.Companion.START_SUNDAY
import kr.co.iotree.todolist.util.getDayOfTheWeek
import kr.co.iotree.todolist.util.getToday

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepository = TodoRepository.getInstance(application)
    private val groupRepository = GroupRepository.getInstance(application)
    private val timeRepository = TimeRepository.getInstance(application)
    private val routineRepository = RoutineRepository.getInstance(application)

    val allCalendarGroup: LiveData<List<TodoGroup>> = groupRepository.readCalendarGroup
    val allTodo: LiveData<List<Todo>> = todoRepository.readAllTodo
    val allRoutine: LiveData<List<Routine>> = routineRepository.readAllRoutine
    val groupTodo = MutableLiveData<List<Todo>>()
    val groupRoutine = MutableLiveData<List<Routine>>()
    val allTime: LiveData<List<TimeAlarm>> = timeRepository.readAllTimeAlarm

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
        it.value = todoRepository.getAllCompleteTodo(String.format("%d%02d01", year.value, month.value).toInt(), String.format("%d%02d31", year.value, month.value).toInt(), true).size
    }

    fun getGroup(groupId: Long): TodoGroup {
        return groupRepository.getGroup(groupId)
    }

    fun getTodo(todoID: Long): Todo {
        return todoRepository.getTodo(todoID)
    }

    fun getGroupTodo(groupId: Long) {
        groupTodo.value = todoRepository.getCalendarTodo(groupId, String.format("%d%02d%02d", year.value, month.value, date.value).toInt(), false)
    }

    fun getGroupRoutine(groupId: Long, year: Int, month: Int, date: Int) {
        groupRoutine.value = if (!pref.getPrefBool(START_SUNDAY, false)) {
            when (getDayOfTheWeek(year, month, date)) {
                0 -> routineRepository.getGroupMondayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                1 -> routineRepository.getGroupTuesdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                2 -> routineRepository.getGroupWednesdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                3 -> routineRepository.getGroupThursdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                4 -> routineRepository.getGroupFridayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                5 -> routineRepository.getGroupSaturdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                6 -> routineRepository.getGroupSundayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                else -> throw RuntimeException("Invalid date")
            }
        } else {
            when (getDayOfTheWeek(year, month, date)) {
                2 -> routineRepository.getGroupMondayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                3 -> routineRepository.getGroupTuesdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                4 -> routineRepository.getGroupWednesdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                5 -> routineRepository.getGroupThursdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                6 -> routineRepository.getGroupFridayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                7 -> routineRepository.getGroupSaturdayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                1 -> routineRepository.getGroupSundayRoutine(groupId, String.format("%d%02d%02d", year, month, date).toInt(), true)
                else -> throw RuntimeException("Invalid date")
            }
        }
    }

    fun changeCompleteCount(year: Int, month: Int) {
        completeCount.value = todoRepository.getAllCompleteTodo(String.format("%d%02d01", year, month).toInt(), String.format("%d%02d31", year, month).toInt(), true).size
    }

    fun updateComplete(complete: Boolean, todoId: Long) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.updateComplete(complete, todoId)
    }

    fun addTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.addTodo(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.deleteTodo(todo)
    }

    fun getAllDayTodo(date: Int): List<Todo> {
        return todoRepository.getAllDayTodo(date)
    }

    fun getAllDayCompleteTodo(date: Int, complete: Boolean): List<Todo> {
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