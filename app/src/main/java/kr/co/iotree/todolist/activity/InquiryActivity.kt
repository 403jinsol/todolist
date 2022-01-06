package kr.co.iotree.todolist.activity

import android.content.Intent
import android.os.Bundle
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.PrefApplication.Companion.pref
import kr.co.iotree.todolist.databinding.ActivityInquiryBinding
import kr.co.iotree.todolist.databinding.ActivitySettingBinding
import kr.co.iotree.todolist.util.LocaleUtil.Companion.OPTION_PHONE_LANGUAGE
import kr.co.iotree.todolist.util.PrefUtil.Companion.LOCALE_CODE
import kr.co.iotree.todolist.util.PrefUtil.Companion.ORDER_COMPLETE
import kr.co.iotree.todolist.util.PrefUtil.Companion.START_SUNDAY

class InquiryActivity : BaseActivity() {
    private lateinit var binding: ActivityInquiryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInquiryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}