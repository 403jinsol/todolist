package kr.co.iotree.todolist.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.iotree.todolist.adapter.GroupAdapter
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.ActivityGroupManageBinding
import kr.co.iotree.todolist.viewModel.GroupListViewModel

class GroupManageActivity : AppCompatActivity() {
    val viewModel: GroupListViewModel by viewModels()
    private var db: TodoDatabase? = null
    lateinit var binding: ActivityGroupManageBinding
    private lateinit var proceedAdapter: GroupAdapter
    private lateinit var completeAdapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGroupManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabase.getInstance(this)
        viewModel.proceedGroups.value = db!!.groupDao().getAllGroup(false)
        viewModel.completeGroups.value = db!!.groupDao().getAllGroup(true)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.addGroup.setOnClickListener {
            val intent = Intent(this, GroupAddActivity::class.java)
            startActivity(intent)
        }

        proceedAdapter = GroupAdapter(viewModel.proceedGroups.value!!)
        binding.proceedGroupRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.proceedGroupRecyclerView.itemAnimator = null
        binding.proceedGroupRecyclerView.adapter = proceedAdapter

        completeAdapter = GroupAdapter(viewModel.completeGroups.value!!)
        binding.completeGroupRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.completeGroupRecyclerView.itemAnimator = null
        binding.completeGroupRecyclerView.adapter = completeAdapter

        setVisibility()
    }

    override fun onResume() {
        super.onResume()
        viewModel.proceedGroups.value = db!!.groupDao().getAllGroup(false)
        viewModel.proceedGroups.observe(this) {
            proceedAdapter.setList(it)
        }

        viewModel.completeGroups.value = db!!.groupDao().getAllGroup(true)
        viewModel.completeGroups.observe(this) {
            completeAdapter.setList(it)
        }

        setVisibility()
    }

    private fun setVisibility() {
        if (viewModel.completeGroups.value!!.size == 0) {
            binding.completeGroupRecyclerView.visibility = View.GONE
            binding.completeTitle.visibility = View.GONE
        } else {
            binding.completeGroupRecyclerView.visibility = View.VISIBLE
            binding.completeTitle.visibility = View.VISIBLE
        }

        if (viewModel.proceedGroups.value!!.size == 0) {
            binding.proceedGroupRecyclerView.visibility = View.GONE
            binding.normalTitle.visibility = View.GONE
        } else {
            binding.proceedGroupRecyclerView.visibility = View.VISIBLE
            binding.normalTitle.visibility = View.VISIBLE
        }

        if (viewModel.completeGroups.value!!.size == 0 && viewModel.proceedGroups.value!!.size == 0)
            binding.noGroup.visibility = View.VISIBLE
        else
            binding.noGroup.visibility = View.GONE

    }
}