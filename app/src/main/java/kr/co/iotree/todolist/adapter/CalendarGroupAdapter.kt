package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.adapter.viewholder.TodoItemViewHolder
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class CalendarGroupAdapter(private val viewModel: CalendarViewModel, private val list: MutableList<Todo>, private val color: Int) : RecyclerView.Adapter<TodoItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        return TodoItemViewHolder(ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bindData(list[position], color)
    }

    override fun getItemCount(): Int = list.size
}