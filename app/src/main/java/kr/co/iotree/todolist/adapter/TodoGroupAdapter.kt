package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.viewholder.TodoItemViewHolder
import kr.co.iotree.todolist.vo.TodoItemVo

class TodoGroupAdapter(private val list: MutableList<TodoItemVo>, val color: String) : RecyclerView.Adapter<TodoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val binding = ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bindData(list[position], color)
    }

    override fun getItemCount(): Int = list.size
}