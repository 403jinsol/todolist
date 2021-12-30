package kr.co.iotree.todolist.activity

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import kr.co.iotree.todolist.activity.PrefActivity.Companion.pref
import kr.co.iotree.todolist.databinding.ActivitySettingLanguageBinding
import kr.co.iotree.todolist.util.PrefUtil.Companion.LOCALE
import java.util.*

class SettingLanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingLanguageBinding
    private lateinit var configuration: Configuration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configuration = resources.configuration

        binding.back.setOnClickListener { onBackPressed() }

        binding.systemContainer.setOnClickListener {
            setLanguageCode(ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language)
        }

        binding.engContainer.setOnClickListener {
            setLanguageCode("en")
        }

        binding.japContainer.setOnClickListener { setLanguageCode("ja") }
    }

    private fun setLanguageCode(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, resources.displayMetrics)

        pref.setPrefString(LOCALE, lang)
    }
}