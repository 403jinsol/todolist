package kr.co.iotree.todolist.adapter.viewholder

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.dialog.TodoDialog
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.databinding.ViewholderTodoItemBinding
import kr.co.iotree.todolist.util.setImageViewColor
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class TodoItemViewHolder(private val binding: ViewholderTodoItemBinding, private val viewModel: CalendarViewModel) : RecyclerView.ViewHolder(binding.root) {
    private val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun bindData(item: Todo, color: Int, supportFragmentManager: FragmentManager) {
        var isCompleted = item.complete

        if (isCompleted) {
            binding.todoIcon.setColorFilter(color)
        } else {
            binding.todoIcon.apply {
                setImageViewColor(this, itemView.context, R.color.todo_icon_default)
            }
        }

        binding.todoIcon.setOnClickListener {
            if (!isCompleted) {
                (it as ImageView).setColorFilter(color)
                isCompleted = !isCompleted
                viewModel.updateComplete(true, item.todoId)
                viewModel.completeCount.value = viewModel.completeCount.value!! + 1
            } else {
                (it as ImageView).apply {
                    setImageViewColor(this, itemView.context, R.color.todo_icon_default)
                }
                isCompleted = !isCompleted
                viewModel.updateComplete(false, item.todoId)
                viewModel.completeCount.value = viewModel.completeCount.value!! - 1
            }
            viewModel.date.value = viewModel.date.value
        }

        binding.moreIcon.setOnClickListener {
            Log.d("☆☆☆☆☆☆☆☆", "${item.date}")
            val bundle = Bundle().apply {
                putLong("todoId", item.todoId!!)
            }

            val dlg = TodoDialog(viewModel).apply {
                arguments = bundle
            }

            dlg.set { _, which ->
                if (which == TodoDialog.EDIT)
                    editTodo(item)
            }.show(supportFragmentManager, "todoDialog")
        }

        binding.todoText.setText(item.content)
    }

    private fun editTodo(item: Todo) {
        binding.todoText.isEnabled = true
        binding.todoText.isFocusableInTouchMode = true
        binding.todoText.requestFocus() //editText 포커스
        imm.showSoftInput(binding.todoText, 0)
        binding.todoText.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.todoText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { // 완료 버튼 클릭하면
                if (binding.todoText.text.isNotEmpty()) {
                    viewModel.updateContentTodo(v.text.toString(), item.todoId!!)
                    return@setOnEditorActionListener true
                } else {
                    imm.hideSoftInputFromWindow(binding.todoText.windowToken, 0)
                }
            }
            return@setOnEditorActionListener false
        }
    }
}