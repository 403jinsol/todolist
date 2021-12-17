package kr.co.iotree.todolist.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.Repository
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.util.GroupColor

class GroupInfoViewModel(application: Application, groupId: Long?) : AndroidViewModel(application) {
    private val repository = Repository(TodoDatabase.getInstance(application, viewModelScope))

    class Factory(private val application: Application, private val groupId: Long?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GroupInfoViewModel(application, groupId) as T
        }
    }

    val groupId = if (groupId != -1L)
        repository.groupDao.getGroup(groupId)!!.groupId
    else
        null

    val group = repository.groupDao.getGroup(groupId)

    var title = MutableLiveData<String>().also {
        if (groupId != -1L)
            it.value = repository.groupDao.getGroup(groupId)!!.title
        else
            it.value = ""
    }

    var color = MutableLiveData<Int>().also {
        if (groupId != -1L)
            it.value = repository.groupDao.getGroup(groupId)!!.color
        else
            it.value = GroupColor.BLACK.color
    }

    var groupPublic = MutableLiveData<Int>().also {
        if (groupId != -1L)
            it.value = repository.groupDao.getGroup(groupId)!!.groupPublic
        else
            it.value = 3
    }

    var complete = MutableLiveData<Boolean>().also {
        if (groupId != -1L)
            it.value = repository.groupDao.getGroup(groupId)!!.complete
        else
            it.value = false
    }

    var reason = MutableLiveData<Int>().also {
        if (groupId != -1L)
            it.value = repository.groupDao.getGroup(groupId)!!.reason
        else
            it.value = 0
    }

    fun addGroup(group: TodoGroup) = viewModelScope.launch(Dispatchers.IO) {
        repository.addGroup(group)
    }

    fun updateGroup(groupId: Long, title: String, groupPublic: Int, color: Int, complete: Boolean, reason: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateGroup(groupId, title, groupPublic, color, complete, reason)
    }

    fun deleteGroup(group: TodoGroup) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteGroup(group)
    }
}