package kr.co.iotree.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData

class GroupRepository(private val dao: GroupDao) {
    companion object {
        @Volatile
        private var INSTANCE: GroupRepository? = null

        fun getInstance(context: Context): GroupRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = GroupRepository(TodoDatabase.getInstance(context).groupDao())
                INSTANCE = instance
                instance
            }
        }
    }

    val readCalendarGroup: LiveData<List<TodoGroup>> = dao.getCalenderGroup(false)
    val readProceedGroup: LiveData<List<TodoGroup>> = dao.getAllGroup(false)
    val readCompleteGroup: LiveData<List<TodoGroup>> = dao.getAllGroup(true)

    fun addGroup(group: TodoGroup) {
        dao.insert(group)
    }

    fun updateGroup(groupId: Long, title: String, groupPublic: Int, color: Int, complete: Boolean, reason: Int) {
        dao.update(groupId, title, groupPublic, color, complete, reason)
    }

    fun deleteGroup(group: TodoGroup) {
        dao.delete(group)
    }

    fun getGroup(groupId: Long): TodoGroup {
        return dao.getGroup(groupId)
    }
}