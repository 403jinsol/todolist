package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.viewholder.StorageTodoViewHolder
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.viewModel.StorageViewModel

class StorageTodoAdapter(private val viewModel: StorageViewModel, private val list: MutableList<Todo>, private var supportFragmentManager: FragmentManager) :
    RecyclerView.Adapter<StorageTodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageTodoViewHolder {
        return StorageTodoViewHolder(ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: StorageTodoViewHolder, position: Int) {
        holder.bindData(list[position], supportFragmentManager)
    }

    override fun getItemCount(): Int = list.size
}