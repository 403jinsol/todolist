package kr.co.iotree.todolist.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.RoutineItemAdapter
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderRoutineGroupBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineGroupViewHolder(private val binding: ViewholderRoutineGroupBinding, private val viewModel: RoutineViewModel) :
    RecyclerView.ViewHolder(binding.root) {
    private val imm = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    lateinit var adapter: RoutineItemAdapter

    fun bindData(group: TodoGroup, supportFragmentManager: FragmentManager) {
        val list = viewModel.getAllGroupRoutine(group.groupId!!)

        binding.title.text = group.title
        binding.title.setTextColor(group.color)
        binding.groupIcon.setColorFilter(group.color)

        adapter = RoutineItemAdapter(viewModel, list, supportFragmentManager)
        binding.recyclerview.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
        binding.recyclerview.itemAnimator = null
        binding.recyclerview.adapter = adapter

        binding.container.setOnClickListener {
            if (binding.editContainer.visibility == View.VISIBLE) {
                binding.editContainer.visibility = View.GONE
                val routine = Routine(
                    null, binding.routineText.text.toString(), 0, Int.MAX_VALUE,
                    sunday = true,
                    monday = true,
                    tuesday = true,
                    wednesday = true,
                    thursday = true,
                    friday = true,
                    saturday = true,
                    groupId = group.groupId
                )
                viewModel.addRoutine(routine)
                binding.routineText.text = null
                imm.hideSoftInputFromWindow(binding.routineText.windowToken, 0) //키보드 안보이게
            }
        }

        binding.titleContainer.setOnClickListener {
            binding.editContainer.visibility = View.VISIBLE
            binding.routineText.isFocusableInTouchMode = true
            binding.routineText.requestFocus() //editText 포커스
            imm.showSoftInput(binding.routineText, 0) //키보드 보이게
        }
    }
}