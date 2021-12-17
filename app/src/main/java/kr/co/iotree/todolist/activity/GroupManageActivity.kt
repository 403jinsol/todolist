package kr.co.iotree.todolist.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.GroupAdapter
import kr.co.iotree.todolist.databinding.ActivityGroupManageBinding
import kr.co.iotree.todolist.viewModel.GroupListViewModel

class GroupManageActivity : AppCompatActivity() {
    val viewModel: GroupListViewModel by viewModels()
    lateinit var binding: ActivityGroupManageBinding
    lateinit var proceedAdapter: GroupAdapter
    lateinit var completeAdapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGroupManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.addGroup.setOnClickListener {
            val intent = Intent(this, GroupAddActivity::class.java)
            startActivity(intent)
        }

        proceedAdapter = GroupAdapter(viewModel, 1)
        binding.proceedGroupRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@GroupManageActivity, RecyclerView.VERTICAL, false)
            itemAnimator = null
            adapter = proceedAdapter
        }

        completeAdapter = GroupAdapter(viewModel, 2)
        binding.completeGroupRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.completeGroupRecyclerView.itemAnimator = null
        binding.completeGroupRecyclerView.adapter = completeAdapter

        viewModel.proceedGroups.observe(this) {
            proceedAdapter.notifyItemRangeChanged(0, viewModel.proceedGroups.value!!.size)
            setVisibility()
        }

        viewModel.completeGroups.observe(this) {
            completeAdapter.notifyItemRangeChanged(0, viewModel.completeGroups.value!!.size)
            setVisibility()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.proceedGroups.observe(this) {
            proceedAdapter.notifyItemRangeChanged(0, viewModel.proceedGroups.value!!.size)
            setVisibility()
        }

        viewModel.completeGroups.observe(this) {
            completeAdapter.notifyItemRangeChanged(0, viewModel.completeGroups.value!!.size)
            setVisibility()
        }
    }

    private fun setVisibility() {
        if (viewModel.proceedGroups.value?.size == 0 || viewModel.proceedGroups.value == null) {
            binding.proceedGroupRecyclerView.visibility = View.GONE
            binding.normalTitle.visibility = View.GONE
        } else {
            binding.proceedGroupRecyclerView.visibility = View.VISIBLE
            binding.normalTitle.visibility = View.VISIBLE
        }
        if (viewModel.completeGroups.value?.size == 0 || viewModel.completeGroups.value == null) {
            binding.completeGroupRecyclerView.visibility = View.GONE
            binding.completeTitle.visibility = View.GONE
        } else {
            binding.completeGroupRecyclerView.visibility = View.VISIBLE
            binding.completeTitle.visibility = View.VISIBLE
        }

        if ((viewModel.proceedGroups.value?.size == 0 || viewModel.proceedGroups.value == null) && (viewModel.completeGroups.value?.size == 0 || viewModel.completeGroups.value == null))
            binding.noGroup.visibility = View.VISIBLE
        else
            binding.noGroup.visibility = View.GONE
    }
}