package kr.co.iotree.todolist.activity.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.DialogTodoBinding
import kr.co.iotree.todolist.util.getNextDay
import kr.co.iotree.todolist.viewModel.CalendarViewModel
import kr.co.iotree.todolist.viewModel.StorageViewModel

class StorageDialog(val viewModel: StorageViewModel) : DialogFragment() {
    lateinit var binding: DialogTodoBinding
    lateinit var todo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!requireArguments().isEmpty) {
            todo = viewModel.getTodo(requireArguments().getLong("todoId", 0))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogTodoBinding.inflate(inflater, container, false)
        binding.dlgTitle.text = todo.content

        if (todo.complete) {
            binding.tomorrowIcon.setImageResource(R.drawable.ic_replay_tomorrow)
            binding.tomorrowText.text = resources.getString(R.string.replay_tomorrow)
            binding.tomorrow.setOnClickListener {
                viewModel.addTodo(Todo(null, todo.content, getNextDay(todo.date).toInt(), complete = false, storage = false, todo.groupId))
                dismiss()
            }
        } else {
            binding.tomorrowIcon.setImageResource(R.drawable.ic_tomorrow)
            binding.tomorrowText.text = resources.getString(R.string.tomorrow)
            binding.tomorrow.setOnClickListener {
                viewModel.updateDateTodo(getNextDay(todo.date).toInt(), todo.todoId!!)
                dismiss()
            }
        }

        setOnClickListener()

        return binding.root
    }

    fun setOnClickListener() {
        binding.edit.setOnClickListener { }

        binding.moveStorage.setOnClickListener {
            viewModel.updateStorageTodo(true, todo.todoId!!)
            dismiss()
        }

        binding.delete.setOnClickListener {
            viewModel.deleteTodo(todo)
            dismiss()
        }
    }
}