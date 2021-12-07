package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.databinding.ViewholderUserNameBinding
import kr.co.iotree.todolist.util.OnItemClick
import kr.co.iotree.todolist.util.getToday
import kr.co.iotree.todolist.viewholder.CalendarViewHolder
import kr.co.iotree.todolist.viewholder.TodoGroupViewHolder
import kr.co.iotree.todolist.viewholder.UserNameViewHolder
import kr.co.iotree.todolist.vo.TodoGroupVo
import java.lang.RuntimeException

class MainAdapter(private val list: MutableList<TodoGroupVo>, private val listener: OnItemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var date = getToday("d").toInt()
    var year = getToday("yyyy").toInt()
    var month = getToday("MM").toInt()
    var isMonth = true

    fun setDate(year: Int, month: Int, date: Int, isMonth: Boolean) {
        this.year = year
        this.month = month
        this.date = date
        this.isMonth = isMonth
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> USER_NAME
            1 -> CALENDAR
            else -> TODO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            USER_NAME -> return UserNameViewHolder(ViewholderUserNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CALENDAR -> return CalendarViewHolder(ViewholderCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            TODO -> return TodoGroupViewHolder(ViewholderTodoGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        throw RuntimeException("Invalid ViewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            USER_NAME -> (holder as UserNameViewHolder).bindData()
            CALENDAR -> (holder as CalendarViewHolder).bindData(listener, isMonth)
            TODO -> (holder as TodoGroupViewHolder).bindData(list[position - 2], year, month, date)
        }
    }

    override fun getItemCount(): Int = 2 + list.size

    companion object {
        const val USER_NAME = 1
        const val CALENDAR = 2
        const val TODO = 3
    }
}