package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.adapter.viewholder.TodoItemViewHolder
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class CalendarGroupAdapter(private val list: MutableList<Todo>, private val color: Int, private val viewModel: CalendarViewModel) : RecyclerView.Adapter<TodoItemViewHolder>() {
    fun addTodo(todo: Todo) {
        list.add(todo)
        notifyItemInserted(itemCount)
    }

    fun deleteTodo(todo: Todo) {
        list.remove(todo)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val binding = ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoItemViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bindData(list[position], color, this)
    }

    override fun getItemCount(): Int = list.size
}