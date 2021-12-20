package kr.co.iotree.todolist.activity.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.DialogTodoBinding
import kr.co.iotree.todolist.util.getNextDay

class TodoDialog : DialogFragment() {
    lateinit var binding: DialogTodoBinding
    lateinit var todo: Todo
    private lateinit var db: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!requireArguments().isEmpty) {
            db = TodoDatabase.getInstance(requireContext(), null)
            todo = db.todoDao().getTodo(requireArguments().getLong("todoId", 0))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogTodoBinding.inflate(inflater, container, false)
        binding.dlgTitle.text = todo.content

        if (todo.complete) {
            binding.tomorrowIcon.setImageResource(R.drawable.ic_replay_tomorrow)
            binding.tomorrowText.text = resources.getString(R.string.replay_tomorrow)
            binding.tomorrow.setOnClickListener {
                db.todoDao().insert(Todo(null, todo.content, getNextDay(todo.date).toInt(), complete = false, storage = false, todo.groupId))
                dismiss()
            }
        } else {
            binding.tomorrowIcon.setImageResource(R.drawable.ic_tomorrow)
            binding.tomorrowText.text = resources.getString(R.string.tomorrow)
            binding.tomorrow.setOnClickListener {
                db.todoDao().updateDate(getNextDay(todo.date).toInt(), todo.todoId)
                dismiss()
            }
        }

        setOnClickListener()

        return binding.root
    }

    fun setOnClickListener() {
        binding.moveStorage.setOnClickListener {
            db.todoDao().updateStorage(true, todo.todoId)
            dismiss()
        }

        binding.delete.setOnClickListener {
            db.todoDao().delete(todo)
            dismiss()
        }
    }
}