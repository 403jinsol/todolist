package kr.co.iotree.todolist.viewModel

import androidx.lifecycle.*
import kr.co.iotree.todolist.database.TodoGroup

class GroupListViewModel : ViewModel() {
    val proceedGroups: MutableLiveData<MutableList<TodoGroup>> by lazy {
        MutableLiveData<MutableList<TodoGroup>>()
    }

    val completeGroups: MutableLiveData<MutableList<TodoGroup>> by lazy {
        MutableLiveData<MutableList<TodoGroup>>()
    }
}