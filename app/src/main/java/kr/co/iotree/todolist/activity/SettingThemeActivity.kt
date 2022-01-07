package kr.co.iotree.todolist.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kr.co.iotree.todolist.activity.BaseApplication.Companion.pref
import kr.co.iotree.todolist.databinding.ActivitySettingThemeBinding
import kr.co.iotree.todolist.util.PrefUtil.Companion.APP_THEME
import kr.co.iotree.todolist.util.PrefUtil.Companion.SYSTEM_THEME

class SettingThemeActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener { onBackPressed() }

        binding.systemContainer.setOnClickListener {
            pref.setPrefString(APP_THEME, SYSTEM_THEME)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            onBackPressed()
        }

        binding.lightContainer.setOnClickListener {
            pref.setPrefString(APP_THEME, "light")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            onBackPressed()
        }

        binding.darkContainer.setOnClickListener {
            pref.setPrefString(APP_THEME, "dark")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            onBackPressed()
        }
    }

}