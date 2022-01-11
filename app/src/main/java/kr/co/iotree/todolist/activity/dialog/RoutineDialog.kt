package kr.co.iotree.todolist.activity.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.databinding.DialogRoutineBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineDialog(val viewModel: RoutineViewModel) : DialogFragment() {
    lateinit var binding: DialogRoutineBinding
    lateinit var routine: Routine
    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!requireArguments().isEmpty) {
            viewModel.viewModelScope.launch {
                routine = viewModel.getRoutine(requireArguments().getLong("routineId", 0))
            }
            type = requireArguments().getString("type", "content")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogRoutineBinding.inflate(inflater, container, false)
        setOnClickListener()
        return binding.root
    }

    private fun setOnClickListener() {
        setEditBtn()
        setDeleteBtn()
        binding.cancelBtn.setOnClickListener { dismiss() }
    }

    private fun setEditBtn() {
        binding.editBtn.setOnClickListener {
            when (type) {
                "content" -> {

                }

                "start" -> {
                    val dlg = DatePickerDialog(requireContext())
                    dlg.setOnDateSetListener { _, year, month, dayOfMonth ->
                        viewModel.updateStartDate(String.format("%d%02d%02d", year, month + 1, dayOfMonth).toInt(), routine.routineId!!)
                        this.dismiss()
                    }
                    dlg.show()
                }
                "end" -> {
                    val dlg = DatePickerDialog(requireContext())
                    dlg.setOnDateSetListener { _, year, month, dayOfMonth ->
                        viewModel.updateEndDate(String.format("%d%02d%02d", year, month + 1, dayOfMonth).toInt(), routine.routineId!!)
                        this.dismiss()
                    }
                    dlg.show()
                }
            }
        }
    }

    private fun setDeleteBtn() {
        binding.deleteBtn.setOnClickListener {
            when (type) {
                "content" -> {
                    viewModel.deleteRoutine(routine)
                    dismiss()
                }
                "start" -> {
                    viewModel.updateStartDate(0, routine.routineId!!)
                    dismiss()
                }
                "end" -> {
                    viewModel.updateEndDate(0, routine.routineId!!)
                    dismiss()
                }

            }
        }
    }
}