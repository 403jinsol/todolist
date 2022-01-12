package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kr.co.iotree.todolist.database.GroupRepository

class GroupListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GroupRepository.getInstance(application)
    val proceedGroups = repository.readProceedGroup
    val completeGroups = repository.readCompleteGroup
}