package kr.co.iotree.todolist.adapter.viewholder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.GroupEditActivity
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderGroupBinding

class GroupViewHolder(private val binding: ViewholderGroupBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(group: TodoGroup) {
        binding.groupTitle.text = group.title
        binding.groupTitle.setTextColor(group.color)

        when (group.reason) {
            1 -> binding.quitIcon.setImageResource(R.drawable.ic_quit1)
            2 -> binding.quitIcon.setImageResource(R.drawable.ic_quit2)
            3 -> binding.quitIcon.setImageResource(R.drawable.ic_quit3)
        }

        binding.groupContainer.setOnClickListener {
            val intent = Intent(itemView.context, GroupEditActivity::class.java)
            intent.putExtra("groupId", group.groupId)
            itemView.context.startActivity(intent)
        }
    }
}