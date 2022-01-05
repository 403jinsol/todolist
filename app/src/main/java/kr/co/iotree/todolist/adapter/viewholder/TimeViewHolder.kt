package kr.co.iotree.todolist.adapter.viewholder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.PrefApplication.Companion.pref
import kr.co.iotree.todolist.activity.dialog.TimePickerDialog
import kr.co.iotree.todolist.database.TimeAlarm
import kr.co.iotree.todolist.databinding.ViewholderTimeBinding
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.PrefUtil
import kr.co.iotree.todolist.viewModel.TimeListViewModel

class TimeViewHolder(
    private val binding: ViewholderTimeBinding, private val viewModel: TimeListViewModel, private val alarmManager: AlarmManager, private val supportFragmentManager: FragmentManager
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("UnspecifiedImmutableFlag")
    fun bindData(alarm: TimeAlarm) {
        if (pref.getPrefString(PrefUtil.LOCALE_CODE, LocaleUtil.OPTION_PHONE_LANGUAGE) == "en")
            binding.time.text = String.format(
                "%d:%02d %s",
                if (alarm.hour < 12) alarm.hour else alarm.hour - 12, alarm.minute,
                if (alarm.hour < 12) itemView.context.resources.getString(R.string.am) else itemView.context.resources.getString(R.string.pm)
            )
        else
            binding.time.text = String.format(
                "%s %d:%02d",
                if (alarm.hour < 12) itemView.context.resources.getString(R.string.am) else itemView.context.resources.getString(R.string.pm),
                if (alarm.hour < 12) alarm.hour else alarm.hour - 12, alarm.minute
            )

        binding.edit.setOnClickListener { TimePickerDialog(viewModel, alarmManager, alarm.timeAlarmId).show(supportFragmentManager, TimePickerDialog.TAG) }

        binding.remove.setOnClickListener {
            viewModel.deleteTime(alarm)
            val intent = Intent(itemView.context, AlarmManager::class.java)
            alarmManager.cancel(PendingIntent.getBroadcast(itemView.context, alarm.allTime, intent, PendingIntent.FLAG_UPDATE_CURRENT))
        }
    }
}