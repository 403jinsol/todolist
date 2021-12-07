package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.databinding.ViewholderUserNameBinding
import kr.co.iotree.todolist.viewholder.CalendarViewHolder
import kr.co.iotree.todolist.viewholder.UserNameViewHolder
import kr.co.iotree.todolist.vo.TodoGroupVo
import java.lang.RuntimeException

class MainAdapter(private val list: MutableList<TodoGroupVo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            USER_NAME
        else
            CALENDAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            USER_NAME -> return UserNameViewHolder(ViewholderUserNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CALENDAR -> return CalendarViewHolder(ViewholderCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        throw RuntimeException("Invalid ViewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            USER_NAME -> (holder as UserNameViewHolder).bindData()
            CALENDAR -> (holder as CalendarViewHolder).bindData(list)
        }
    }

    override fun getItemCount(): Int = 2

    companion object {
        const val USER_NAME = 1
        const val CALENDAR = 2
    }
}