package kr.co.iotree.todolist.activity.dialog

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.annotation.IntRange
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.iotree.todolist.broadCast.AlarmReceiver
import kr.co.iotree.todolist.database.TimeAlarm
import kr.co.iotree.todolist.databinding.DialogTimePickerBinding
import kr.co.iotree.todolist.viewModel.TimeListViewModel
import java.util.*
import kotlin.collections.ArrayList

class TimePickerDialog(val viewModel: TimeListViewModel, private val alarmManager: AlarmManager, private val alarmId: Long?) : BottomSheetDialogFragment() {
    lateinit var binding: DialogTimePickerBinding

    private fun TimePicker.setTimeInterval(
        @IntRange(from = 0, to = 30)
        timeInterval: Int = DEFAULT_INTERVAL
    ) {
        try {
            val classForId = Class.forName("com.android.internal.R\$id")
            val fieldId = classForId.getField("minute").getInt(null)

            (this.findViewById(fieldId) as NumberPicker).apply {
                minValue = MINUTES_MIN
                maxValue = MINUTES_MAX / timeInterval - 1
                displayedValues = getDisplayedValue(timeInterval)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun TimePicker.getDisplayedMinute(
        @IntRange(from = 0, to = 30)
        timeInterval: Int = DEFAULT_INTERVAL
    ): Int = minute * timeInterval

    private fun getDisplayedValue(
        @IntRange(from = 0, to = 30)
        timeInterval: Int = DEFAULT_INTERVAL
    ): Array<String> {
        val minutesArray = ArrayList<String>()
        for (i in 0 until MINUTES_MAX step timeInterval) {
            minutesArray.add("%02d".format(i))
        }
        return minutesArray.toArray(arrayOf(""))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogTimePickerBinding.inflate(inflater, container, false)
//        binding.timePicker.setTimeInterval()

        binding.complete.setOnClickListener {
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute
//            val minute = binding.timePicker.getDisplayedMinute()
            val allTime = "%d%02d".format(hour, minute).toInt()

            if (alarmId == null) {
                setAlarm(allTime, hour, minute)
            } else {
                editAlarm(alarmId, allTime, hour, minute)
            }
            dismiss()
        }
        return binding.root
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun editAlarm(alarmId: Long, allTime: Int, hour: Int, minute: Int) {

        val intent = Intent(requireContext(), AlarmManager::class.java)
        alarmManager.cancel(PendingIntent.getBroadcast(requireContext(), viewModel.getTime(alarmId)!!.allTime, intent, PendingIntent.FLAG_UPDATE_CURRENT))
        viewModel.deleteTime(viewModel.getTime(alarmId)!!)
        setAlarm(allTime, hour, minute)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(allTime: Int, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), allTime, intent, PendingIntent.FLAG_UPDATE_CURRENT) // BroadcastReceiver를 시작하는 intent
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent) // RTC_WAKEUP: 지정된 시간에 펜딩 인텐트를 작동시키기 위해 디바이스를 깨움

        viewModel.addTime(TimeAlarm(null, allTime, hour, minute))
    }

    companion object {
        const val TAG = "TimePickDialog"
        const val DEFAULT_INTERVAL = 5
        const val MINUTES_MIN = 0
        const val MINUTES_MAX = 60
    }
}
