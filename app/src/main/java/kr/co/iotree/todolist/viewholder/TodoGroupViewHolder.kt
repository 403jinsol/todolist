package kr.co.iotree.todolist.viewholder

import android.app.AlertDialog
import android.graphics.Color
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.TodoGroupAdapter
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.vo.TodoGroupVo

class TodoGroupViewHolder(private val binding: ViewholderTodoGroupBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(group: TodoGroupVo) {
        val db = TodoDatabase.getInstance(itemView.context)
        val todoList = db?.todoDao()?.getTodo(group.title, "20211130")

        val adapter = TodoGroupAdapter(todoList!!, group.color)

        binding.title.text = group.title
        binding.title.setTextColor(Color.parseColor(group.color))
        binding.recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = adapter

        binding.add.setOnClickListener {
            val editText = EditText(itemView.context)
            val dlg = AlertDialog.Builder(itemView.context)
            dlg.setTitle("일정 추가")
            dlg.setView(editText)
            dlg.setPositiveButton("추가") { _, _ ->

                val todo = Todo(null, editText.text.toString(), group.title, "20211130", false)
                db.todoDao().insert(todo)
                adapter.addTodo(todo)
            }
            dlg.show()
        }
    }
}