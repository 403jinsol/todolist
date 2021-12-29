package kr.co.iotree.todolist.adapter.viewholder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.database.TimeAlarm
import kr.co.iotree.todolist.databinding.ViewholderTimeBinding
import kr.co.iotree.todolist.viewModel.TimeListViewModel

class TimeViewHolder(private val binding: ViewholderTimeBinding, private val viewModel: TimeListViewModel, private val alarmManager: AlarmManager) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n", "UnspecifiedImmutableFlag")
    fun bindData(alarm: TimeAlarm) {
        binding.time.text = "%s %d:%02d".format(if (alarm.hour < 12) "오전" else "오후", if (alarm.hour < 12) alarm.hour else alarm.hour - 12, alarm.minute)

        binding.remove.setOnClickListener {
            viewModel.deleteTime(alarm)
            val intent = Intent(itemView.context, AlarmManager::class.java)
            alarmManager.cancel(PendingIntent.getBroadcast(itemView.context, alarm.allTime, intent, PendingIntent.FLAG_UPDATE_CURRENT))
        }
    }
}