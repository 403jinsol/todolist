package kr.co.iotree.todolist.activity

import android.os.Bundle
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
        }

        viewModel.allRoutine.observe(this) {
            adapter.notifyItemRangeChanged(0, it.size)
        }
    }
}