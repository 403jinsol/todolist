package kr.co.iotree.todolist.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.RoutineGroupAdapter
import kr.co.iotree.todolist.databinding.ActivityRoutineBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineActivity : BaseActivity() {
    val viewModel: RoutineViewModel by viewModels()
    lateinit var binding: ActivityRoutineBinding
    lateinit var adapter: RoutineGroupAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        adapter = RoutineGroupAdapter(viewModel, supportFragmentManager)
        binding.routineRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.routineRecyclerView.itemAnimator = null
        binding.routineRecyclerView.adapter = adapter

        viewModel.allGroup.observe(this) {
            adapter.notifyItemRangeChanged(0, it.size)
            setVisibility()
        }

        viewModel.allRoutine.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }

    private fun setVisibility() {
        if (viewModel.allGroup.value?.size == 0 || viewModel.allGroup.value == null) {
            binding.routineRecyclerView.visibility = View.GONE
            binding.noRoutine.visibility = View.VISIBLE
        } else {
            binding.routineRecyclerView.visibility = View.VISIBLE
            binding.noRoutine.visibility = View.GONE
        }
    }
}