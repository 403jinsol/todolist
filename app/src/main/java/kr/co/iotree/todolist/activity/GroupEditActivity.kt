package kr.co.iotree.todolist.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.iotree.todolist.adapter.ColorAdapter
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ActivityGroupEditBinding
import kr.co.iotree.todolist.util.GroupColor
import kr.co.iotree.todolist.viewModel.AddGroupViewModel

class GroupEditActivity : AppCompatActivity() {
    lateinit var adapter: ColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityGroupEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: AddGroupViewModel by viewModels()
        val db = TodoDatabase.getInstance(this)
        val group = db!!.groupDao().getGroup(intent.getLongExtra("groupId", 0))

        adapter = ColorAdapter(viewModel)

        binding.groupTitle.setText(group.title)
        binding.groupTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                viewModel.title.value = binding.groupTitle.text.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.title.value = binding.groupTitle.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.title.value = binding.groupTitle.text.toString()
            }
        })

        viewModel.color.value = group.color

        viewModel.color.observe(this) {
            binding.groupTitle.setTextColor(it)
            binding.groupTitle.backgroundTintList = ColorStateList.valueOf(it)
        }

        viewModel.index.observe(this) {
            adapter.setIndex(viewModel.index.value!!)
        }

        binding.colorRecyclerView.layoutManager = GridLayoutManager(this, 6)
        binding.colorRecyclerView.itemAnimator = null
        binding.colorRecyclerView.adapter = adapter

        binding.back.setOnClickListener { onBackPressed() }

        binding.checkGroup.setOnClickListener {
            db.groupDao().insert(TodoGroup(null, viewModel.title.value!!, viewModel.color.value!!))
            onBackPressed()
        }

        binding.deleteBtn.setOnClickListener {
            db.groupDao().delete(group)
            onBackPressed()
        }
    }
}