package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.viewholder.StorageGroupViewHolder
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.viewModel.StorageViewModel

class StorageGroupAdapter(private val viewModel: StorageViewModel, private var supportFragmentManager: FragmentManager) : RecyclerView.Adapter<StorageGroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageGroupViewHolder {
        return StorageGroupViewHolder(ViewholderTodoGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: StorageGroupViewHolder, position: Int) {
        holder.bindData(viewModel.storageGroup.value!![position], supportFragmentManager)
    }

    override fun getItemCount(): Int = viewModel.storageGroup.value?.size ?: 0
}