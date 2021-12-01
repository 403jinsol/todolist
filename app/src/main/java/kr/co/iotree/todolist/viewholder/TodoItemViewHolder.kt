package kr.co.iotree.todolist.viewholder

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.adapter.TodoGroupAdapter
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.util.setImageViewColor

class TodoItemViewHolder(private val binding: ViewholderTodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val db = TodoDatabase.getInstance(itemView.context)

    fun bindData(item: Todo, color: String, host: TodoGroupAdapter) {
        var isCompleted = item.complete

        if (isCompleted) {
            binding.todoIcon.setColorFilter(Color.parseColor(color))
        } else {
            binding.todoIcon.apply {
                setImageViewColor(this, itemView.context, R.color.todo_icon_default)
            }
        }

        binding.todoIcon.setOnClickListener {
            if (!isCompleted) {
                (it as ImageView).setColorFilter(Color.parseColor(color))
                isCompleted = !isCompleted
                db!!.todoDao().updateComplete(true, item.id)
            } else {
                (it as ImageView).apply {
                    setImageViewColor(this, itemView.context, R.color.todo_icon_default)
                }
                isCompleted = !isCompleted
                db!!.todoDao().updateComplete(false, item.id)
            }
        }

        binding.moreIcon.setOnClickListener {
            val dlg = AlertDialog.Builder(itemView.context)
            dlg.setTitle("일정 변경")
            dlg.setItems(R.array.todoMore) { _, pos ->
                if (pos == 1) {
                    db!!.todoDao().delete(item)
                    host.deleteTodo(item)
                }
            }
            dlg.show()
        }

        binding.todoText.text = item.content
    }
}