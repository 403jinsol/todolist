package kr.co.iotree.todolist.activity

import android.os.Bundle
import kr.co.iotree.todolist.activity.PrefApplication.Companion.pref
import kr.co.iotree.todolist.databinding.ActivitySettingLanguageBinding
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.PrefUtil.Companion.LOCALE_CODE

class SettingLanguageActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener { onBackPressed() }

        binding.systemContainer.setOnClickListener { updateAppLocale(LocaleUtil.OPTION_PHONE_LANGUAGE) }

        binding.engContainer.setOnClickListener { updateAppLocale("en") }

        binding.korContainer.setOnClickListener { updateAppLocale("ko") }

        binding.japContainer.setOnClickListener { updateAppLocale("ja") }
    }

    private fun updateAppLocale(locale: String) {
        pref.setPrefString(LOCALE_CODE, locale)
        LocaleUtil.applyLocalizedContext(this, locale)
        onBackPressed()
    }

}