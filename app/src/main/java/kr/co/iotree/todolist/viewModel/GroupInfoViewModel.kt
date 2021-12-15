package kr.co.iotree.todolist.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.iotree.todolist.util.GroupColor

class GroupInfoViewModel : ViewModel() {
    var title = MutableLiveData<String>()

    var color = MutableLiveData<Int>()

    init {
        color.value = GroupColor.BLACK.color
    }

    var groupPublic = MutableLiveData<Int>()

    init {
        groupPublic.value = 3
    }

    var complete = MutableLiveData<Boolean>()

    init {
        complete.value = true
    }

    var reason = MutableLiveData<Int>()

    init {
        reason.value = 0
    }
}