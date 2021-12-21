package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kr.co.iotree.todolist.database.Repository
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup

class GroupListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(TodoDatabase.getInstance(application).todoDao(), TodoDatabase.getInstance(application).groupDao())
    val proceedGroups = repository.readProceedGroup
    val completeGroups = repository.readCompleteGroup
}