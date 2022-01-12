package kr.co.iotree.todolist.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderRoutineCalendarBinding
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class TodoRoutineViewHolder(private val binding: ViewholderRoutineCalendarBinding, private val viewModel: CalendarViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: Routine, groupId: Long) {
        binding.todoText.text = item.content

        binding.container.setOnClickListener {
            viewModel.addTodo(Todo(null, item.content, String.format("%d%02d%02d", viewModel.year.value, viewModel.month.value, viewModel.date.value).toInt(), false, false, groupId))
        }
    }
}