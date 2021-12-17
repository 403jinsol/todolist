package kr.co.iotree.todolist.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.CalendarGroupAdapter
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class CalendarGroupViewHolder(private val binding: ViewholderTodoGroupBinding, private val viewModel: CalendarViewModel) : RecyclerView.ViewHolder(binding.root) {
    private val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    lateinit var adapter: CalendarGroupAdapter

    fun bindData(group: TodoGroup, year: Int, month: Int, date: Int) {
        val todoDate = "$year$month$date".toInt()

        //FIXME
        val db = TodoDatabase.getInstance(itemView.context, null)
        val list = db.todoDao().getTodo(group.groupId, todoDate)

//        adapter = CalendarGroupAdapter(viewModel, group.color)
        adapter = CalendarGroupAdapter(viewModel, list, group.color)

        binding.title.text = group.title
        binding.title.setTextColor(group.color)
        binding.recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = adapter

        binding.container.setOnClickListener { // 빈 공간 클릭
            if (binding.todoEdit.text.isNotEmpty()) { // editText 내용 있으면(입력했으면)
                val todo = Todo(null, binding.todoEdit.text.toString(), todoDate, false, group.groupId)
                insertTodo(todo)
                hideTodoEditText()
            } else {
                hideTodoEditText()
            }
        }

        binding.titleContainer.setOnClickListener { // 그룹 제목 클릭
            binding.editContainer.visibility = View.VISIBLE //입력창 보이게
            binding.todoEdit.isFocusableInTouchMode = true
            binding.todoEdit.requestFocus() //editText 포커스
            imm.showSoftInput(binding.todoEdit, 0) //키보드 보이게
        }

        binding.todoEdit.imeOptions = EditorInfo.IME_ACTION_DONE // 버튼 완료로 나오게
        binding.todoEdit.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { // 완료 버튼 클릭하면
                if (binding.todoEdit.text.isNotEmpty()) {
                    val todo = Todo(null, v.text.toString(), todoDate, false, group.groupId)
                    insertTodo(todo)
                    return@setOnEditorActionListener true
                } else {
                    hideTodoEditText()
                }
            }
            return@setOnEditorActionListener false
        }
    }

    private fun insertTodo(todo: Todo) {
        viewModel.addTodo(todo)
        binding.todoEdit.text = null
        viewModel.completeCount.value = viewModel.completeCount.value!! + 1
    }

    private fun hideTodoEditText() {
        imm.hideSoftInputFromWindow(binding.todoEdit.windowToken, 0) //키보드 안보이게
        binding.todoEdit.text = null // 텍스트 초기화
        binding.editContainer.visibility = View.GONE //editText 안보이게
    }
}