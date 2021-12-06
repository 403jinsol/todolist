package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.viewholder.CalendarViewHolder
import kr.co.iotree.todolist.viewholder.TodoGroupViewHolder
import kr.co.iotree.todolist.vo.TodoGroupVo

class TodoAdapter(private val list: MutableList<TodoGroupVo>, private val holder: CalendarViewHolder) : RecyclerView.Adapter<TodoGroupViewHolder>() {
    fun addTodoGroup(todoGroup: TodoGroupVo) {
        list.add(todoGroup)
        notifyItemInserted(itemCount)
    }

    fun deleteTodoGroup(todoGroup: TodoGroupVo) {
        list.remove(todoGroup)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoGroupViewHolder {
        val binding = ViewholderTodoGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoGroupViewHolder, position: Int) {
        holder.bindData(list[position], this.holder)
    }

    override fun getItemCount(): Int = list.size
}