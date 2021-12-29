package kr.co.iotree.todolist.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.iotree.todolist.activity.PrefActivity.Companion.pref
import kr.co.iotree.todolist.databinding.ActivitySettingBinding
import kr.co.iotree.todolist.util.PrefUtil.Companion.START_SUNDAY

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarSwitch.isChecked = pref.getPrefBool(START_SUNDAY, false)

        binding.calendarSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                pref.setPrefBool(START_SUNDAY, true)
            else
                pref.setPrefBool(START_SUNDAY, false)
        }
    }
}