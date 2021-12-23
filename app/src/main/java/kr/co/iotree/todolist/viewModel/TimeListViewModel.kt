package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.*

class TimeListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TimeRepository(TodoDatabase.getInstance(application).timeAlarmDao())
    val allTimes = repository.readAllTimeAlarm

    fun addTime(timeAlarm: TimeAlarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.addTimeAlarm(timeAlarm)
    }

    fun deleteTime(timeAlarm: TimeAlarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTimeAlarm(timeAlarm)
    }
}