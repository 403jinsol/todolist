package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.viewholder.GroupViewHolder
import kr.co.iotree.todolist.databinding.ViewholderGroupBinding
import kr.co.iotree.todolist.viewModel.GroupListViewModel

class GroupAdapter(private var viewModel: GroupListViewModel, private val code: Int) : RecyclerView.Adapter<GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(ViewholderGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        if (code == 1)
            holder.bindData(viewModel.proceedGroups.value!![position])
        else
            holder.bindData(viewModel.completeGroups.value!![position])
    }

    override fun getItemCount(): Int {
        return if (code == 1)
            viewModel.proceedGroups.value?.size ?: 0
        else
            viewModel.completeGroups.value?.size ?: 0
    }
}