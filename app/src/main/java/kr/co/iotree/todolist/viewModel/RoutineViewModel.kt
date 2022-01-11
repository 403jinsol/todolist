package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.database.RoutineRepository
import kr.co.iotree.todolist.database.TodoGroupRepository

class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RoutineRepository.getInstance(application)

    val allRoutine = repository.readAllTimeAlarm
    val allGroup = TodoGroupRepository.getInstance(application).readProceedGroup

    fun getRoutine(routineId: Long): Routine {
        return repository.getRoutine(routineId)
    }

    fun getGroupRoutine(groupId: Long): MutableList<Routine> {
        return repository.getGroupRoutine(groupId)
    }

    fun addRoutine(routine: Routine) {
        repository.addRoutine(routine)
    }

    fun deleteRoutine(routine: Routine) {
        repository.deleteRoutine(routine)
    }

    fun updateStartDate(startDate: Int, routineId: Long) {
        repository.updateStartDate(startDate, routineId)
    }

    fun updateEndDate(endDate: Int, routineId: Long) {
        repository.updateEndDate(endDate, routineId)
    }
}