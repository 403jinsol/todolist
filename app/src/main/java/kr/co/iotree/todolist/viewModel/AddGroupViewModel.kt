package kr.co.iotree.todolist.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.iotree.todolist.util.GroupColor

class AddGroupViewModel : ViewModel() {
    var title = MutableLiveData<String>()

    var color = MutableLiveData<Int>()

    init {
        color.value = GroupColor.BLACK.color
    }

    var index = MutableLiveData<Int>()

    init {
        index.value = 0
    }
}