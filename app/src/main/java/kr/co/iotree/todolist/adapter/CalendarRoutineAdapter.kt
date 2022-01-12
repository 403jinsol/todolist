package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.viewholder.TodoRoutineViewHolder
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.databinding.ViewholderRoutineCalendarBinding
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class CalendarRoutineAdapter(private val viewModel: CalendarViewModel, private val list: List<Routine>, private val groupId: Long) : RecyclerView.Adapter<TodoRoutineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoRoutineViewHolder {
        return TodoRoutineViewHolder(ViewholderRoutineCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: TodoRoutineViewHolder, position: Int) {
        holder.bindData(list[position], groupId)
    }

    override fun getItemCount(): Int = list.size
}