package kr.co.iotree.todolist.activity.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.databinding.DialogCompleteBinding

class CompleteDialog : DialogFragment() {
    lateinit var binding: DialogCompleteBinding
    private var reason = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!requireArguments().isEmpty)
            reason = requireArguments().getInt("reason", 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogCompleteBinding.inflate(inflater, container, false)

        when (reason) {
            1 -> {
                binding.dlgTitle.text = resources.getString(R.string.quit1)
                binding.dlgContent.text = resources.getString(R.string.complete_dialog_content1)
            }
            2 -> {
                binding.dlgTitle.text = resources.getString(R.string.quit2)
                binding.dlgContent.text = resources.getString(R.string.complete_dialog_content2)
            }
            3 -> {
                binding.dlgTitle.text = resources.getString(R.string.quit3)
                binding.dlgContent.text = resources.getString(R.string.complete_dialog_content3)
            }
        }

        binding.checkBtn.setOnClickListener { dismiss() }

        return binding.root
    }
}