package kr.co.iotree.todolist.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.adapter.CalendarRoutineAdapter
import kr.co.iotree.todolist.adapter.CalendarTodoAdapter
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class CalendarGroupViewHolder(private val binding: ViewholderTodoGroupBinding, private val viewModel: CalendarViewModel, private var supportFragmentManager: FragmentManager) :
    RecyclerView.ViewHolder(binding.root) {
    private val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    private lateinit var todoAdapter: CalendarTodoAdapter
    private lateinit var routineAdapter: CalendarRoutineAdapter

    fun bindData(group: TodoGroup, year: Int, month: Int, date: Int) {
        val todoDate = String.format("%d%02d%02d", year, month, date).toInt()

        binding.title.text = group.title
        binding.title.setTextColor(group.color)
        binding.groupIcon.setColorFilter(group.color)

        viewModel.getGroupTodo(group.groupId!!)
        val todoList = viewModel.groupTodo.value!!
        viewModel.getGroupRoutine(group.groupId, year, month, date)
        val routineList = viewModel.groupRoutine.value!!

        todoAdapter = CalendarTodoAdapter(viewModel, todoList, group.color, supportFragmentManager)
        binding.todoRecyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
        binding.todoRecyclerview.adapter = todoAdapter

        routineAdapter = CalendarRoutineAdapter(viewModel, routineList, group.groupId)
        binding.routineRecyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
        binding.routineRecyclerview.adapter = routineAdapter

        setOnClickListener(group, todoDate)
    }

    private fun setOnClickListener(group: TodoGroup, todoDate: Int) {
        binding.container.setOnClickListener { // 빈 공간 클릭
            if (binding.todoEdit.text.isNotEmpty()) { // editText 내용 있으면(입력했으면)
                val todo = Todo(null, binding.todoEdit.text.toString(), todoDate, complete = false, storage = false, group.groupId)
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
                    val todo = Todo(null, v.text.toString(), todoDate, complete = false, storage = false, group.groupId)
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
        viewModel.viewModelScope.launch {
            viewModel.addTodo(todo)
        }
        binding.todoEdit.text = null
        viewModel.completeCount.value = viewModel.completeCount.value!! + 1
    }

    private fun hideTodoEditText() {
        imm.hideSoftInputFromWindow(binding.todoEdit.windowToken, 0) //키보드 안보이게
        binding.todoEdit.text = null // 텍스트 초기화
        binding.editContainer.visibility = View.GONE //editText 안보이게
    }
}