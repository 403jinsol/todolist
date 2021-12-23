package kr.co.iotree.todolist.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.StorageGroupAdapter
import kr.co.iotree.todolist.databinding.ActivityStorageBinding
import kr.co.iotree.todolist.viewModel.StorageViewModel

class StorageActivity : AppCompatActivity() {
    val viewModel: StorageViewModel by viewModels()
    lateinit var binding: ActivityStorageBinding
    lateinit var adapter: StorageGroupAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        adapter = StorageGroupAdapter(viewModel, supportFragmentManager)
        binding.storageRecyclerView.layoutManager = LinearLayoutManager(this@StorageActivity, RecyclerView.VERTICAL, false)
        binding.storageRecyclerView.itemAnimator = null
        binding.storageRecyclerView.adapter = adapter

        viewModel.storageGroup.observe(this) {
            adapter.notifyItemRangeChanged(0, viewModel.storageGroup.value!!.size)
        }

        viewModel.storageTodo.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }
}