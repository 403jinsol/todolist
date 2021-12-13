package kr.co.iotree.todolist.activity

import android.content.Intent
import android.os.Bundle
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
    lateinit var adapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityGroupManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: GroupListViewModel by viewModels()
        db = TodoDatabase.getInstance(this)
        viewModel.groups.value = db!!.groupDao().getAllTodoGroup()

        adapter = GroupAdapter(viewModel.groups.value!!)
        binding.groupRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.groupRecyclerView.itemAnimator = null
        binding.groupRecyclerView.adapter = adapter

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.addGroup.setOnClickListener {
            val intent = Intent(this, GroupAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.groups.value = db!!.groupDao().getAllTodoGroup()
        viewModel.groups.observe(this) {
            adapter.setList(it)
        }
    }
}