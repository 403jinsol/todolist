package kr.co.iotree.todolist.viewholder

import android.graphics.Color
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.TodoGroupAdapter
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.vo.TodoGroupVo

class TodoGroupViewHolder(private val binding: ViewholderTodoGroupBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(group: TodoGroupVo, holder: CalendarViewHolder) {
        val date = "${holder.year}_${holder.month}+${holder.date}"

        val db = TodoDatabase.getInstance(itemView.context)
        val todoList = db?.todoDao()?.getTodo(group.title, date)

        val adapter = TodoGroupAdapter(todoList!!, group.color)

        binding.title.text = group.title
        binding.title.setTextColor(Color.parseColor(group.color))
        binding.recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = adapter

        binding.todoEdit.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.todoEdit.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val item = Todo(null, v.text.toString(), group.title, date, false)
                db.todoDao().insert(item)
                adapter.addTodo(item)
                binding.editContainer.visibility = View.GONE
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener true
        }

        binding.titleContainer.setOnClickListener {
            binding.editContainer.visibility = View.VISIBLE
        }

        binding.container.setOnClickListener {
            val item = Todo(null, binding.todoEdit.text.toString(), group.title, date, false)
            db.todoDao().insert(item)
            adapter.addTodo(item)
        }
    }
}