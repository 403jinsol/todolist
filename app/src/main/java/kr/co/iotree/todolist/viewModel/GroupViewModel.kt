package kr.co.iotree.todolist.viewModel

import androidx.lifecycle.*
import kr.co.iotree.todolist.database.TodoGroup

class GroupViewModel : ViewModel() {
    val groups: MutableLiveData<MutableList<TodoGroup>> by lazy {
        MutableLiveData<MutableList<TodoGroup>>()
    }
}