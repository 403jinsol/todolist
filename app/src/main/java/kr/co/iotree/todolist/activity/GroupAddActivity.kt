package kr.co.iotree.todolist.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.adapter.ColorAdapter
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ActivityGroupAddBinding
import kr.co.iotree.todolist.viewModel.GroupInfoViewModel

class GroupAddActivity : AppCompatActivity() {
    val viewModel: GroupInfoViewModel by viewModels()
    lateinit var binding: ActivityGroupAddBinding
    lateinit var adapter: ColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = TodoDatabase.getInstance(this)
        adapter = ColorAdapter(viewModel)

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

        binding.colorRecyclerView.layoutManager = GridLayoutManager(this, 6)
        binding.colorRecyclerView.itemAnimator = null
        binding.colorRecyclerView.adapter = adapter

        viewModel.color.observe(this) {
            binding.groupTitle.setTextColor(it)
            binding.groupTitle.backgroundTintList = ColorStateList.valueOf(it)
            adapter.setColor(viewModel.color.value!!)
        }

        setRadioGroupEvent()
        setOnClickListener(db!!)

        binding.public3.isChecked = true
    }

    private fun setRadioGroupEvent() {
        binding.publicSetting.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.public1.id, binding.public2.id -> {
                    val toast = Toast(this).apply {
                        view = View.inflate(this@GroupAddActivity, R.layout.toast_group, null)
                    }
                    toast.show()
                    binding.publicSetting.clearCheck()
                }
                R.id.public3 -> viewModel.groupPublic.value = 3
                R.id.public4 -> viewModel.groupPublic.value = 4
            }
        }
    }

    private fun setOnClickListener(db: TodoDatabase) {
        binding.back.setOnClickListener { onBackPressed() }

        binding.addGroup.setOnClickListener {
            db.groupDao().insert(TodoGroup(null, viewModel.title.value!!, viewModel.groupPublic.value!!, viewModel.color.value!!, false, 0))
            onBackPressed()
        }
    }
}