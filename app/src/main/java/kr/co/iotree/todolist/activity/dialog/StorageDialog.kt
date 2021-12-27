package kr.co.iotree.todolist.activity.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.DialogStorageBinding
import kr.co.iotree.todolist.viewModel.StorageViewModel

class StorageDialog(val viewModel: StorageViewModel) : DialogFragment() {
    lateinit var binding: DialogStorageBinding
    lateinit var todo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!requireArguments().isEmpty) {
            todo = viewModel.getTodo(requireArguments().getLong("todoId", 0))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogStorageBinding.inflate(inflater, container, false)
        binding.dlgTitle.text = todo.content

        setOnClickListener()

        return binding.root
    }

    fun setOnClickListener() {
        binding.edit.setOnClickListener { }

        binding.changeDate.setOnClickListener {
            val dlg = DatePickerDialog(requireContext())
            dlg.setOnDateSetListener { _, year, month, dayOfMonth ->
                Log.d("☆☆☆☆☆☆☆", "year: $year")
                Log.d("☆☆☆☆☆☆☆", "month+1: ${month + 1}")
                Log.d("☆☆☆☆☆☆☆", "dayOfMonth: $dayOfMonth")
                Log.d("☆☆☆☆☆☆☆", "yearMonth+1dayOfMonth: $year${month + 1}$dayOfMonth")
                viewModel.updateDateTodo("$year${month + 1}$dayOfMonth".toInt(), todo.todoId!!)
                viewModel.updateStorageTodo(false, todo.todoId!!)
            }
            dlg.show()
        }

        binding.delete.setOnClickListener {
            viewModel.deleteTodo(todo)
            dismiss()
        }
    }
}