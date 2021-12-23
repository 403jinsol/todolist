package kr.co.iotree.todolist.adapter.viewholder

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.dialog.StorageDialog
import kr.co.iotree.todolist.activity.dialog.TodoDialog
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.viewModel.StorageViewModel

class StorageTodoViewHolder(private val binding: ViewholderTodoItemBinding, private val viewModel: StorageViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(item: Todo, supportFragmentManager: FragmentManager) {

        binding.todoIcon.setOnClickListener {
            val toast = Toast(itemView.context).apply {
                view = View.inflate(itemView.context, R.layout.toast_storage_complete, null)
            }
            toast.show()
        }

        binding.moreIcon.setOnClickListener {
            val bundle = Bundle().apply {
                putLong("todoId", item.todoId!!)
            }

            val dlg = StorageDialog(viewModel).apply {
                arguments = bundle
            }
            dlg.show(supportFragmentManager, "todoDialog")
        }

        binding.todoText.setText(item.content)
    }
}