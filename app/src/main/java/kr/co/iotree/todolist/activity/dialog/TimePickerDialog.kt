package kr.co.iotree.todolist.activity.dialog

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.iotree.todolist.activity.TimeManageActivity
import kr.co.iotree.todolist.database.TimeAlarm
import kr.co.iotree.todolist.databinding.DialogTimePickerBinding
import kr.co.iotree.todolist.util.AlarmReceiver
import kr.co.iotree.todolist.viewModel.TimeListViewModel

class TimePickerDialog(val viewModel: TimeListViewModel) : BottomSheetDialogFragment() {
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
    ): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        minute * timeInterval
    } else {
        currentMinute * timeInterval
    }

    private fun getDisplayedValue(
        @IntRange(from = 0, to = 30)
        timeInterval: Int = DEFAULT_INTERVAL
    ): Array<String> {
        val minutesArray = ArrayList<String>()
        for (i in 0 until MINUTES_MAX step timeInterval) {
            minutesArray.add(i.toString())
        }

        return minutesArray.toArray(arrayOf(""))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogTimePickerBinding.inflate(inflater, container, false)
        binding.timePicker.setTimeInterval()

        binding.complete.setOnClickListener {
            val hour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.timePicker.hour
            } else {
                binding.timePicker.currentHour
            }
            val minute = binding.timePicker.getDisplayedMinute()

            viewModel.addTime(TimeAlarm(null, "$hour$minute".toInt(), hour, minute))

            setAlarm()
            dismiss()
        }
        return binding.root
    }

    private fun setAlarm() {
        val alarmManager = getSystemService(requireContext(), ALAME) as AlarmManager

        val intent = Intent(requireContext(), AlarmReceiver::class.java)  // 1
        val pendingIntent = PendingIntent.getBroadcast(     // 2
            requireContext(), AlarmReceiver.NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val repeatInterval = AlarmManager.INTERVAL_DAY
        val triggerTime = (SystemClock.elapsedRealtime() + repeatInterval)
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, pendingIntent)
    }

    companion object {
        const val TAG = "TimePickDialog"
        const val DEFAULT_INTERVAL = 5
        const val MINUTES_MIN = 0
        const val MINUTES_MAX = 60
    }
}
