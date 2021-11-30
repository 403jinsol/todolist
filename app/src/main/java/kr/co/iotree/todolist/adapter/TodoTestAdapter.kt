package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.viewholder.CalendarViewHolder
import kr.co.iotree.todolist.viewholder.TodoGroupViewHolder
import kr.co.iotree.todolist.viewholder.TodoItemViewHolder
import kr.co.iotree.todolist.viewholder.TodoTestViewHolder
import kr.co.iotree.todolist.vo.TodoGroupVo

class TodoTestAdapter(private val list: MutableList<Todo>, private val color: String) : RecyclerView.Adapter<TodoTestViewHolder>() {
    fun addTodo(todo: Todo) {
        list.add(todo)
        notifyItemInserted(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTestViewHolder {
        val binding = ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoTestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoTestViewHolder, position: Int) {
        holder.bindData(list[position], color)
    }

    override fun getItemCount(): Int = list.size
}