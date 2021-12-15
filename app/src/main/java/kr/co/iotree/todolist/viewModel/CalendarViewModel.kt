package kr.co.iotree.todolist.viewModel

import androidx.lifecycle.*
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.util.getToday

class CalendarViewModel : ViewModel() {
    val groups: MutableLiveData<MutableList<TodoGroup>> by lazy {
        MutableLiveData<MutableList<TodoGroup>>()
    }

    var year = MutableLiveData<Int>().also {
        it.value = getToday("yyyy")
    }

    var month = MutableLiveData<Int>().also {
        it.value = getToday("MM")
    }

    val date = MutableLiveData<Int>().also {
        it.value = getToday("d")
    }

    val isMonth = MutableLiveData<Boolean>().also {
        it.value = true
    }

    val completeCount = MutableLiveData<Int>().also {
        it.value = 0
    }
}