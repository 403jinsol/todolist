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
import kr.co.iotree.todolist.activity.dialog.CompleteDialog
import kr.co.iotree.todolist.adapter.ColorAdapter
import kr.co.iotree.todolist.databinding.ActivityGroupEditBinding
import kr.co.iotree.todolist.viewModel.GroupInfoViewModel

class GroupEditActivity : AppCompatActivity() {
    lateinit var binding: ActivityGroupEditBinding
    lateinit var adapter: ColorAdapter
    val viewModel by viewModels<GroupInfoViewModel> {
        GroupInfoViewModel.Factory(application, intent.getLongExtra("groupId", -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGroupEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preComplete = viewModel.complete

        setViewModel()

        binding.groupTitle.setText(viewModel.group!!.title)
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

        setOnclickListener(preComplete.value!!)
        setRadioGroupEvent()
    }

    private fun setViewModel() {
        viewModel.color.observe(this) {
            binding.groupTitle.setTextColor(it)
            binding.groupTitle.backgroundTintList = ColorStateList.valueOf(it)
            adapter.setColor(viewModel.color.value!!)
        }
    }

    private fun setRadioGroupEvent() {
        if (viewModel.groupPublic.value == 3)
            binding.public3.isChecked = true
        if (viewModel.groupPublic.value == 4)
            binding.public4.isChecked = true

        binding.publicSetting.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.public1.id, binding.public2.id -> {
                    val toast = Toast(this).apply {
                        view = View.inflate(this@GroupEditActivity, R.layout.toast_group, null)
                    }
                    toast.show()
                    binding.publicSetting.clearCheck()
                    if (viewModel.groupPublic.value == 3)
                        binding.public3.isChecked = true
                    if (viewModel.groupPublic.value == 4)
                        binding.public4.isChecked = true
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

    private fun setOnclickListener(preComplete: Boolean) {
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

        binding.checkGroup.setOnClickListener { //확인 버튼
            if (binding.groupTitle.text.isEmpty()) { //이름 입력 안했을때
                val toast = Toast(this).apply {
                    view = View.inflate(this@GroupEditActivity, R.layout.toast_no_name, null)
                }
                toast.show()
                return@setOnClickListener
            }

            if (viewModel.complete.value!! && viewModel.reason.value!! == 0) { //종료 이유 선택 안했을때
                val toast = Toast(this).apply {
                    view = View.inflate(this@GroupEditActivity, R.layout.toast_reason, null)
                }
                toast.show()
                return@setOnClickListener
            }

            viewModel.updateGroup(
                intent.getLongExtra("groupId", -1),
                viewModel.title.value!!,
                viewModel.groupPublic.value!!,
                viewModel.color.value!!,
                viewModel.complete.value!!,
                viewModel.reason.value!!
            )

            if (!preComplete && viewModel.complete.value!!) { //목표 종료하면 메인으로
                val bundle = Bundle().apply {
                    putInt("reason", viewModel.reason.value!!)
                }
                val dlg = CompleteDialog().apply {
                    arguments = bundle
                }
                dlg.show(supportFragmentManager, "completeDialog")
            } else {
                onBackPressed()
            }
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.deleteGroup(viewModel.group!!)
            onBackPressed()
        }
    }
}