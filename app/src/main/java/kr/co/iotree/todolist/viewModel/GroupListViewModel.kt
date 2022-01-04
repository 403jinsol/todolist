package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kr.co.iotree.todolist.database.TodoGroupRepository

class GroupListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoGroupRepository.getInstance(application)
    val proceedGroups = repository.readProceedGroup
    val completeGroups = repository.readCompleteGroup
}