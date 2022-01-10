package kr.co.iotree.todolist.adapter.viewholder

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.RoutineItemAdapter
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderRoutineGroupBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineGroupViewHolder(private val binding: ViewholderRoutineGroupBinding, private val viewModel: RoutineViewModel) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var adapter: RoutineItemAdapter

    fun bindData(group: TodoGroup, supportFragmentManager: FragmentManager) {
        val list = viewModel.getGroupRoutine(group.groupId!!)

        binding.title.text = group.title

        adapter = RoutineItemAdapter(viewModel, list, supportFragmentManager)
        binding.recyclerview.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
        binding.recyclerview.itemAnimator = null
        binding.recyclerview.adapter = adapter
        binding.recyclerview
    }
}