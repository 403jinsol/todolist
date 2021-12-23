package kr.co.iotree.todolist.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.activity.dialog.TimePickerDialog
import kr.co.iotree.todolist.adapter.TimeAdapter
import kr.co.iotree.todolist.databinding.ActivityTimeManageBinding
import kr.co.iotree.todolist.viewModel.TimeListViewModel

class TimeManageActivity : AppCompatActivity() {
    val viewModel: TimeListViewModel by viewModels()
    lateinit var binding: ActivityTimeManageBinding
    lateinit var adapter: TimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTimeManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListener()

        adapter = TimeAdapter(viewModel)
        binding.timeRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.timeRecyclerView.itemAnimator = null
        binding.timeRecyclerView.adapter = adapter

        viewModel.allTimes.observe(this) {
            adapter.notifyItemRangeChanged(0, it.size)
            setVisibility()
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
            TimePickerDialog(viewModel).show(supportFragmentManager, TimePickerDialog.TAG)
        }
    }
}