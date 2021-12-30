package kr.co.iotree.todolist.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.iotree.todolist.activity.PrefActivity.Companion.pref
import kr.co.iotree.todolist.databinding.ActivitySettingBinding
import kr.co.iotree.todolist.util.PrefUtil.Companion.LOCALE
import kr.co.iotree.todolist.util.PrefUtil.Companion.ORDER_COMPLETE
import kr.co.iotree.todolist.util.PrefUtil.Companion.START_SUNDAY

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener { onBackPressed() }

        binding.calendarSwitch.isChecked = pref.getPrefBool(START_SUNDAY, false)

        binding.calendarSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                pref.setPrefBool(START_SUNDAY, true)
            else
                pref.setPrefBool(START_SUNDAY, false)
        }

        binding.orderSwitch.isChecked = pref.getPrefBool(ORDER_COMPLETE, false)

        binding.orderSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                pref.setPrefBool(ORDER_COMPLETE, true)
            else
                pref.setPrefBool(ORDER_COMPLETE, false)
        }

        binding.languageValue.text = pref.getPrefString(LOCALE, "System")
        binding.languageContainer.setOnClickListener { startActivity(Intent(this, SettingLanguageActivity::class.java)) }
    }
}