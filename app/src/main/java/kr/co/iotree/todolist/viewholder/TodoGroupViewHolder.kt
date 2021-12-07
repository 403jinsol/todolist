package kr.co.iotree.todolist.viewholder

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.TodoGroupAdapter
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.vo.TodoGroupVo

class TodoGroupViewHolder(private val binding: ViewholderTodoGroupBinding) : RecyclerView.ViewHolder(binding.root) {
    private val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun bindData(group: TodoGroupVo, year:Int, month:Int, date:Int) {
        val todoDate = "$year$month$date"

        val db = TodoDatabase.getInstance(itemView.context)
        val todoList = db?.todoDao()?.getTodo(group.title, todoDate)

        val adapter = TodoGroupAdapter(todoList!!, group.color)

        binding.title.text = group.title
        binding.title.setTextColor(Color.parseColor(group.color))
        binding.recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = adapter

        binding.container.setOnClickListener { // 빈 공간 클릭
            if (binding.todoEdit.text.isNotEmpty()) { // editText 내용 있으면(입력했으면)
                val item = Todo(null, binding.todoEdit.text.toString(), group.title, todoDate, false)
                db.todoDao().insert(item)
                adapter.addTodo(item)
                hideTodo()
            } else {
                hideTodo()
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
                    val item = Todo(null, v.text.toString(), group.title, todoDate, false)
                    db.todoDao().insert(item)
                    adapter.addTodo(item)
                    binding.todoEdit.text = null
                    return@setOnEditorActionListener true
                } else {
                    hideTodo()
                }
            }
            return@setOnEditorActionListener false
        }
    }

    private fun hideTodo() {
        imm.hideSoftInputFromWindow(binding.todoEdit.windowToken, 0) //키보드 안보이게
        binding.todoEdit.text = null // 텍스트 초기화
        binding.editContainer.visibility = View.GONE //editText 안보이게
    }
}