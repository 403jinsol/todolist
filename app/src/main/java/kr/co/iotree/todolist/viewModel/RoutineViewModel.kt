package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.database.RoutineRepository
import kr.co.iotree.todolist.database.TodoGroupRepository

class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RoutineRepository.getInstance(application)

    val allRoutine = repository.readAllTimeAlarm
    val allGroup = TodoGroupRepository.getInstance(application).readProceedGroup

    fun getGroupRoutine(groupId: Long): LiveData<MutableList<Routine>> {
        return repository.getGroupRoutine(groupId)
    }
}