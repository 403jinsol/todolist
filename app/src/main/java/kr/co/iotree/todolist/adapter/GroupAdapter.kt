package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderGroupBinding
import kr.co.iotree.todolist.adapter.viewholder.GroupViewHolder

class GroupAdapter(private var list: MutableList<TodoGroup>) : RecyclerView.Adapter<GroupViewHolder>() {

    fun setList(list: MutableList<TodoGroup>) {
        this.list = list
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(ViewholderGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size
}