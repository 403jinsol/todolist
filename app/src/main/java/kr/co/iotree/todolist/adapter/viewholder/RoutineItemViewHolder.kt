package kr.co.iotree.todolist.adapter.viewholder

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.CalendarGroupAdapter
import kr.co.iotree.todolist.adapter.RoutineGroupAdapter
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ViewholderRoutineGroupBinding
import kr.co.iotree.todolist.databinding.ViewholderRoutineItemBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineItemViewHolder(private val binding: ViewholderRoutineItemBinding, private val viewModel: RoutineViewModel) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var adapter: CalendarGroupAdapter

    fun bindData(routine: Routine, supportFragmentManager: FragmentManager) {
    }
}