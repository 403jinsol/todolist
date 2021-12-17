package kr.co.iotree.todolist.adapter.viewholder

import android.app.AlertDialog
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.util.setImageViewColor
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class TodoItemViewHolder(private val binding: ViewholderTodoItemBinding, private val viewModel: CalendarViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: Todo, color: Int) {
        var isCompleted = item.complete

        if (isCompleted) {
            binding.todoIcon.setColorFilter(color)
        } else {
            binding.todoIcon.apply {
                setImageViewColor(this, itemView.context, R.color.todo_icon_default)
            }
        }

        binding.todoIcon.setOnClickListener {
            if (!isCompleted) {
                (it as ImageView).setColorFilter(color)
                isCompleted = !isCompleted
                viewModel.updateComplete(true, item.todoId)
                viewModel.completeCount.value = viewModel.completeCount.value!! + 1
            } else {
                (it as ImageView).apply {
                    setImageViewColor(this, itemView.context, R.color.todo_icon_default)
                }
                isCompleted = !isCompleted
                viewModel.updateComplete(false, item.todoId)
                viewModel.completeCount.value = viewModel.completeCount.value!! - 1
            }
            viewModel.date.value = viewModel.date.value
        }

        binding.moreIcon.setOnClickListener {
            val dlg = AlertDialog.Builder(itemView.context)
            dlg.setTitle("일정 변경")
            dlg.setItems(R.array.todoMore) { _, pos ->
                if (pos == 1) {
                    viewModel.deleteTodo(item)
                    viewModel.completeCount.value = viewModel.completeCount.value!! - 1
                }
            }
            dlg.show()
        }

        binding.todoText.text = item.content
    }
}