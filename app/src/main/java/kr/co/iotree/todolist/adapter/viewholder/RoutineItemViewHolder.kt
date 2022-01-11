package kr.co.iotree.todolist.adapter.viewholder

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.activity.dialog.RoutineDialog
import kr.co.iotree.todolist.adapter.CalendarGroupAdapter
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.databinding.ViewholderRoutineItemBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineItemViewHolder(private val binding: ViewholderRoutineItemBinding, private val viewModel: RoutineViewModel) :
    RecyclerView.ViewHolder(binding.root) {
    lateinit var adapter: CalendarGroupAdapter

    fun bindData(routine: Routine, supportFragmentManager: FragmentManager) {
        binding.content.setText(routine.content)
        binding.moreIcon.setOnClickListener {
            val bundle = Bundle().apply {
                putLong("routineId", routine.routineId!!)
                putString("type", "content")
            }

            val dlg = RoutineDialog(viewModel).apply {
                arguments = bundle
            }
            dlg.show(supportFragmentManager, "todoDialog")
        }

        binding.startDateContainer.setOnClickListener {
            if (routine.startDate == 0) {
                showStartDateDialog(itemView.context, routine)
            } else {
                val bundle = Bundle().apply {
                    putLong("routineId", routine.routineId!!)
                    putString("type", "start")
                }

                val dlg = RoutineDialog(viewModel).apply {
                    arguments = bundle
                }
                dlg.show(supportFragmentManager, "todoDialog")
            }
        }

        binding.endDateContainer.setOnClickListener {
            if (routine.endDate == 0) {
                showEndDateDialog(itemView.context, routine)
            } else {
                val bundle = Bundle().apply {
                    putLong("routineId", routine.routineId!!)
                    putString("type", "end")
                }

                val dlg = RoutineDialog(viewModel).apply {
                    arguments = bundle
                }
                dlg.show(supportFragmentManager, "todoDialog")
            }
        }

        changeDateText(routine)
    }

    private fun showStartDateDialog(context: Context, routine: Routine) {
        val dlg = DatePickerDialog(context)
        dlg.setOnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.updateStartDate(String.format("%04d%02d%02d", year, month + 1, dayOfMonth).toInt(), routine.routineId!!)
            changeDateText(routine)
        }
        dlg.show()
    }

    private fun showEndDateDialog(context: Context, routine: Routine) {
        val dlg = DatePickerDialog(context)
        dlg.setOnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.updateEndDate(String.format("%04d%02d%02d", year, month + 1, dayOfMonth).toInt(), routine.routineId!!)
            changeDateText(routine)
        }
        dlg.show()
    }

    private fun changeDateText(routine: Routine) {
        if (routine.startDate == 0)
            binding.startDate.text = ""
        else
            binding.startDate.text = String.format("%04d. %02d. %02d.", routine.startDate / 10000, routine.startDate % 10000 / 100, routine.startDate % 100)

        if (routine.endDate == 0)
            binding.endDate.text = ""
        else
            binding.endDate.text = String.format("%04d. %02d. %02d.", routine.endDate / 10000, routine.endDate % 10000 / 100, routine.endDate % 100)
    }
}