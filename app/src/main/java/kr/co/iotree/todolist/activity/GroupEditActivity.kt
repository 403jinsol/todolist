package kr.co.iotree.todolist.activity

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.dialog.CompleteDialog
import kr.co.iotree.todolist.adapter.ColorAdapter
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ActivityGroupEditBinding
import kr.co.iotree.todolist.util.dpToPx
import kr.co.iotree.todolist.viewModel.GroupInfoViewModel

class GroupEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupEditBinding
    lateinit var adapter: ColorAdapter
    val viewModel: GroupInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGroupEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = TodoDatabase.getInstance(this)
        val group = db!!.groupDao().getGroup(intent.getLongExtra("groupId", 0))
        val preComplete = group.complete

        setViewModel(group)

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

        if (viewModel.complete.value!!) {
            binding.quitGroup.visibility = View.VISIBLE
            binding.quitBtn.setImageResource(R.drawable.ic_radio_checked)
        }

        when (viewModel.reason.value!!) {
            0 -> binding.quitGroup.check(-1)
            1 -> binding.quitGroup.check(binding.quit1.id)
            2 -> binding.quitGroup.check(binding.quit2.id)
            3 -> binding.quitGroup.check(binding.quit3.id)
        }

        adapter = ColorAdapter(viewModel)
        binding.colorRecyclerView.layoutManager = GridLayoutManager(this, 6)
        binding.colorRecyclerView.itemAnimator = null
        binding.colorRecyclerView.adapter = adapter

        setOnclickListener(db, group, preComplete)
        setRadioGroupEvent(group)
    }

    private fun setViewModel(group: TodoGroup) {
        viewModel.title.value = group.title
        viewModel.groupPublic.value = group.groupPublic
        viewModel.complete.value = group.complete
        viewModel.color.value = group.color
        viewModel.reason.value = group.reason

        viewModel.color.observe(this) {
            binding.groupTitle.setTextColor(it)
            binding.groupTitle.backgroundTintList = ColorStateList.valueOf(it)
            adapter.setColor(viewModel.color.value!!)
        }
    }

    private fun setRadioGroupEvent(group: TodoGroup) {
        if (group.groupPublic == 3)
            binding.public3.isChecked = true
        if (group.groupPublic == 4)
            binding.public4.isChecked = true

        binding.publicSetting.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.public1.id, binding.public2.id -> {
                    val toast = Toast(this).apply {
                        view = View.inflate(this@GroupEditActivity, R.layout.toast_group, null)
                    }
                    toast.show()
                    binding.publicSetting.clearCheck()
                }
                R.id.public3 -> viewModel.groupPublic.value = 3
                R.id.public4 -> viewModel.groupPublic.value = 4
            }
        }

        binding.quitGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.quit1.id -> viewModel.reason.value = 1
                binding.quit2.id -> viewModel.reason.value = 2
                binding.quit3.id -> viewModel.reason.value = 3
            }
        }
    }

    private fun setOnclickListener(db: TodoDatabase, group: TodoGroup, preComplete: Boolean) {
        binding.back.setOnClickListener { onBackPressed() }

        binding.quitContainer.setOnClickListener {
            if (binding.quitGroup.visibility == View.GONE) {
                binding.quitGroup.visibility = View.VISIBLE
                binding.quitBtn.setImageResource(R.drawable.ic_radio_checked)
                viewModel.complete.value = true
            } else {
                binding.quitGroup.visibility = View.GONE
                binding.quitBtn.setImageResource(R.drawable.ic_radio_uncheck)
                viewModel.complete.value = false
                viewModel.reason.value = 0
            }
        }

        binding.checkGroup.setOnClickListener {
            db.groupDao().update(group.groupId, viewModel.title.value!!, viewModel.groupPublic.value!!, viewModel.color.value!!, viewModel.complete.value!!, viewModel.reason.value!!)
            Log.d("sdfsgsdfgsdgdgdgsdgasdf", "setOnclickListener: ${viewModel.reason.value!!}")
            Log.d("sdfsgsdfgsdgdgdgsdgasdf", "setOnclickListener: ${viewModel.complete.value!!}")
            if (viewModel.complete.value!! && viewModel.reason.value!! == 0) { //종료 이유 선택 안했을때
                val toast = Toast(this).apply {
                    view = View.inflate(this@GroupEditActivity, R.layout.toast_reason, null)
                }
                toast.show()
            } else if (!preComplete && viewModel.complete.value!!) { //목표 종료하면 메인으로
                val intent = Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                startActivity(intent)
            } else {
                onBackPressed()
            }
        }

        binding.deleteBtn.setOnClickListener {
            db.groupDao().delete(group)
            onBackPressed()
        }
    }
}