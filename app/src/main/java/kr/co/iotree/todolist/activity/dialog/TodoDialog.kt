package kr.co.iotree.todolist.activity.dialog

import android.app.DatePickerDialog
import android.content.DialogInterface
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

class TodoDialog(val viewModel: CalendarViewModel) : DialogFragment() {
    lateinit var binding: DialogTodoBinding
    lateinit var todo: Todo
    private var clickListener: DialogInterface.OnClickListener? = null

    private var which = Int.MIN_VALUE

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

    fun set(clickListener: DialogInterface.OnClickListener?): TodoDialog {
        this.clickListener = clickListener
        return this
    }

    fun setOnClickListener() {
        binding.edit.setOnClickListener {
            which = EDIT
            dismiss()
        }

        binding.changeDate.setOnClickListener {
            val dlg = DatePickerDialog(requireContext())
            dlg.setOnDateSetListener { _, year, month, dayOfMonth ->
                viewModel.updateDateTodo("$year${month + 1}$dayOfMonth".toInt(), todo.todoId!!)
                this.dismiss()
            }
            dlg.show()
        }

        binding.moveStorage.setOnClickListener {
            viewModel.updateStorageTodo(true, todo.todoId!!)
            dismiss()
        }

        binding.delete.setOnClickListener {
            viewModel.deleteTodo(todo)
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        // 창 닫히면 which 넣어서 onClick
        if (which != Integer.MIN_VALUE)
            clickListener?.onClick(dialog, which)
    }

    companion object {
        const val EDIT = 1
    }
}