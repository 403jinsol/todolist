package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kr.co.iotree.todolist.database.TodoGroupRepository
import kr.co.iotree.todolist.database.TodoDatabase

class GroupListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoGroupRepository(TodoDatabase.getInstance(application).todoDao(), TodoDatabase.getInstance(application).groupDao())
    val proceedGroups = repository.readProceedGroup
    val completeGroups = repository.readCompleteGroup
}