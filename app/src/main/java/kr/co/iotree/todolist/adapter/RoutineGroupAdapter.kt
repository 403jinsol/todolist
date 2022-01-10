package kr.co.iotree.todolist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.viewholder.RoutineGroupViewHolder
import kr.co.iotree.todolist.databinding.ViewholderRoutineGroupBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineGroupAdapter(private val viewModel: RoutineViewModel, private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<RoutineGroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineGroupViewHolder {
        return RoutineGroupViewHolder(ViewholderRoutineGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
    }

    override fun onBindViewHolder(holder: RoutineGroupViewHolder, position: Int) {
        holder.bindData(viewModel.allGroup.value!![position], supportFragmentManager)
    }

    override fun getItemCount(): Int = viewModel.allGroup.value?.size ?: 0
}