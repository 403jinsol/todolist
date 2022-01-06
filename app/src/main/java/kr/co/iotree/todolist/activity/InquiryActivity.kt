package kr.co.iotree.todolist.activity

import android.os.Bundle
import kr.co.iotree.todolist.databinding.ActivityInquiryBinding

class InquiryActivity : BaseActivity() {
    private lateinit var binding: ActivityInquiryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInquiryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}