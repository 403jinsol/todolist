package kr.co.iotree.todolist.viewholder

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.activity.GroupEditActivity
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderGroupBinding

class GroupViewHolder(private val binding: ViewholderGroupBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(group: TodoGroup) {
        binding.groupTitle.text = group.title
        binding.groupTitle.setTextColor(group.color)

        binding.moreBtn.setOnClickListener {
            val intent = Intent(itemView.context, GroupEditActivity::class.java)
            intent.putExtra("groupId", group.groupId)
            itemView.context.startActivity(intent)
        }
    }
}