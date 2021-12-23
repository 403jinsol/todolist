package kr.co.iotree.todolist.adapter.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.TimeAlarm
import kr.co.iotree.todolist.databinding.ViewholderTimeBinding
import kr.co.iotree.todolist.viewModel.TimeListViewModel

class TimeViewHolder(private val binding: ViewholderTimeBinding, private val viewModel: TimeListViewModel) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bindData(alarm: TimeAlarm) {
        if (alarm.hour >= 12) {
            if (alarm.hour - 12 == 0)
                binding.time.text = "오후 ${12}:${alarm.minute}"
            else
                binding.time.text = "오후 ${alarm.hour - 12}:${alarm.minute}"
        } else {
            if (alarm.hour == 0)
                binding.time.text = "오전 ${12}:${alarm.minute}"
            else
                binding.time.text = "오전 ${alarm.hour}:${alarm.minute}"
        }

        binding.remove.setOnClickListener {
            viewModel.deleteTime(alarm)
        }
    }
}