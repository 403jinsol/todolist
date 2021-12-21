package kr.co.iotree.todolist.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kr.co.iotree.todolist.databinding.ActivityGroupManageBinding
import kr.co.iotree.todolist.databinding.ActivityTimeManageBinding
import kr.co.iotree.todolist.viewModel.GroupListViewModel

class TimeManageActivity : AppCompatActivity() {
    val viewModel: GroupListViewModel by viewModels()
    lateinit var binding: ActivityTimeManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}