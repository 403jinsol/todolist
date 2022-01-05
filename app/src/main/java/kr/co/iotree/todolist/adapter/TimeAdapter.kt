package kr.co.iotree.todolist.adapter

import android.app.AlarmManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.viewholder.TimeViewHolder
import kr.co.iotree.todolist.databinding.ViewholderTimeBinding
import kr.co.iotree.todolist.viewModel.TimeListViewModel

class TimeAdapter(private val viewModel: TimeListViewModel, private val alarmManager: AlarmManager, private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<TimeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(ViewholderTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel, alarmManager, supportFragmentManager)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bindData(viewModel.allTimes.value!![position])
    }

    override fun getItemCount() = viewModel.allTimes.value?.size ?: 0
}