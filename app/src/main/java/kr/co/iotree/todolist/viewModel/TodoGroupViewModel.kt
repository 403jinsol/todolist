package kr.co.iotree.todolist.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.iotree.todolist.vo.TodoGroupVo

class TodoGroupViewModel : ViewModel() {
    val todoGroups: MutableLiveData<List<TodoGroupVo>> by lazy {
        MutableLiveData<List<TodoGroupVo>>().also {
            setTodoGroupList(null)
        }
    }

    fun setTodoGroupList(todoGroupList: MutableList<TodoGroupVo>?) {
        todoGroups.value = todoGroupList ?: mutableListOf()
    }
}