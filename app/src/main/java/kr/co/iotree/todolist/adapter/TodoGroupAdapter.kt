package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.viewholder.TodoItemViewHolder

class TodoGroupAdapter(private val list: MutableList<Todo>, private val color: String) : RecyclerView.Adapter<TodoItemViewHolder>() {
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
        return TodoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bindData(list[position], color, this)
    }

    override fun getItemCount(): Int = list.size
}