package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.GroupRepository
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.database.RoutineRepository

class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RoutineRepository.getInstance(application)

    val allRoutine = repository.readAllTimeAlarm
    val allGroup = GroupRepository.getInstance(application).readProceedGroup

    fun getRoutine(routineId: Long): Routine {
        return repository.getRoutine(routineId)
    }

    fun addRoutine(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.addRoutine(routine)
    }

    fun deleteRoutine(routine: Routine) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRoutine(routine)
    }

    fun updateContent(content: String, routineId: Long) {
        repository.updateContent(content, routineId)
    }

    fun updateStartDate(startDate: Int, routineId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateStartDate(startDate, routineId)
    }

    fun updateEndDate(endDate: Int, routineId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateEndDate(endDate, routineId)
    }

    fun updateDay(mon: Boolean, tue: Boolean, wed: Boolean, thu: Boolean, fri: Boolean, sat: Boolean, sun: Boolean, routineId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDay(mon, tue, wed, thu, fri, sat, sun, routineId)
    }

    fun getAllGroupRoutine(groupId: Long): List<Routine> {
        return repository.getAllGroupRoutine(groupId)
    }
}