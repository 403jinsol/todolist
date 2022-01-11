package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.viewholder.RoutineItemViewHolder
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.databinding.ViewholderRoutineItemBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineItemAdapter(private val viewModel: RoutineViewModel, private val list: List<Routine>, private val supportFragmentManager: FragmentManager) :
    RecyclerView.Adapter<RoutineItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineItemViewHolder {
        return RoutineItemViewHolder(ViewholderRoutineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: RoutineItemViewHolder, position: Int) {
        holder.bindData(list[position], supportFragmentManager)
    }

    override fun getItemCount(): Int = list.size
}