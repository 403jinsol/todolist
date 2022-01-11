package kr.co.iotree.todolist.activity

import android.app.AlarmManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.activity.dialog.TimePickerDialog
import kr.co.iotree.todolist.adapter.TimeAdapter
import kr.co.iotree.todolist.databinding.ActivityTimeManageBinding
import kr.co.iotree.todolist.viewModel.TimeListViewModel

class TimeManageActivity : BaseActivity() {
    val viewModel: TimeListViewModel by viewModels()
    lateinit var binding: ActivityTimeManageBinding
    lateinit var adapter: TimeAdapter
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        setOnClickListener()

        adapter = TimeAdapter(viewModel, alarmManager, supportFragmentManager)
        binding.timeRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.timeRecyclerView.itemAnimator = null
        binding.timeRecyclerView.adapter = adapter

        setVisibility()

        viewModel.allTimes.observe(this) {
            adapter.notifyItemRangeChanged(0, it.size)
        }
    }

    private fun setVisibility() {
        if (viewModel.allTimes.value?.size == 0 || viewModel.allTimes.value == null) {
            binding.timeRecyclerView.visibility = View.GONE
            binding.noTimeAlarm.visibility = View.VISIBLE
        } else {
            binding.timeRecyclerView.visibility = View.VISIBLE
            binding.noTimeAlarm.visibility = View.GONE
        }
    }

    private fun setOnClickListener() {
        binding.back.setOnClickListener { onBackPressed() }

        binding.addTime.setOnClickListener {
            TimePickerDialog(viewModel, alarmManager, null).show(supportFragmentManager, TimePickerDialog.TAG)
        }
    }
}