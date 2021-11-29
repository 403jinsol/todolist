package kr.co.iotree.todolist.viewholder

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.MainAdapter
import kr.co.iotree.todolist.adapter.TodoGroupAdapter
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.vo.TodoGroupVo
import kr.co.iotree.todolist.vo.TodoItemVo

class TodoGroupViewHolder(private val binding: ViewholderTodoGroupBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(group: TodoGroupVo) {
        val adapter = TodoGroupAdapter(group.todoItem, group.color)

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
                group.todoItem.add(TodoItemVo(editText.text.toString(), false))
                adapter.notifyItemInserted(adapter.itemCount)
            }
            dlg.show()
        }
    }
}