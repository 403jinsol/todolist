package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.viewholder.CalendarViewHolder
import kr.co.iotree.todolist.viewholder.TodoGroupViewHolder
import kr.co.iotree.todolist.vo.TodoGroupVo

class MainAdapter(private val list: List<TodoGroupVo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            CALENDER
        else
            TODO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CALENDER -> {
                val binding = ViewholderCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CalendarViewHolder(binding)
            }
            TODO -> {
                val binding = ViewholderTodoGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TodoGroupViewHolder(binding)
            }
            else -> throw RuntimeException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CALENDER -> (holder as CalendarViewHolder).bindData()
            TODO -> (holder as TodoGroupViewHolder).bindData(list[position - 1])
            else -> throw RuntimeException("Invalid ViewType")
        }
    }

    override fun getItemCount(): Int = list.size + 1

    companion object {
        private const val CALENDER = 1
        private const val TODO = 2
    }
}