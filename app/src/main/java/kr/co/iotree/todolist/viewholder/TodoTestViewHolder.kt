package kr.co.iotree.todolist.viewholder

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding

class TodoTestViewHolder(private val binding: ViewholderTodoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(item: Todo, color: String) {
        binding.todoIcon.setOnClickListener {
            if (!item.complete) {
                (it as ImageView).imageTintList = ColorStateList.valueOf(Color.parseColor(color))
                item.complete = true
            } else {
                (it as ImageView).imageTintList = ColorStateList.valueOf(Color.parseColor("#FF434343"))
                item.complete = false
            }
        }
        binding.todoText.text = item.content
    }
}