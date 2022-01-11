package kr.co.iotree.todolist.activity

import android.os.Bundle
import androidx.activity.viewModels
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.database.Routine
import kr.co.iotree.todolist.databinding.ActivityRoutineDayBinding
import kr.co.iotree.todolist.viewModel.RoutineViewModel

class RoutineDayActivity : BaseActivity() {
    val viewModel: RoutineViewModel by viewModels()
    private lateinit var binding: ActivityRoutineDayBinding
    lateinit var routine: Routine

    var mon = false
    var tue = false
    var wed = false
    var thu = false
    var fri = false
    var sat = false
    var sun = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoutineDayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        routine = viewModel.getRoutine(intent.getLongExtra("routineId", -1))

        setVisibility()
        setValue()
        setOnclickListener()
    }

    private fun setVisibility() {
        if (routine.monday)
            binding.mondayBtn.setImageResource(R.drawable.ic_radio_checked)
        if (routine.tuesday)
            binding.tuesdayBtn.setImageResource(R.drawable.ic_radio_checked)
        if (routine.wednesday)
            binding.wednesdayBtn.setImageResource(R.drawable.ic_radio_checked)
        if (routine.thursday)
            binding.thursdayBtn.setImageResource(R.drawable.ic_radio_checked)
        if (routine.friday)
            binding.fridayBtn.setImageResource(R.drawable.ic_radio_checked)
        if (routine.saturday)
            binding.saturdayBtn.setImageResource(R.drawable.ic_radio_checked)
        if (routine.sunday)
            binding.sundayBtn.setImageResource(R.drawable.ic_radio_checked)
    }

    private fun setValue() {
        mon = routine.monday
        tue = routine.tuesday
        wed = routine.wednesday
        thu = routine.thursday
        fri = routine.friday
        sat = routine.saturday
        sun = routine.sunday
    }

    private fun setOnclickListener() {
        binding.back.setOnClickListener { onBackPressed() }

        binding.mondayContainer.setOnClickListener {
            if (mon)
                binding.mondayBtn.setImageResource(R.drawable.ic_radio_uncheck)
            else
                binding.mondayBtn.setImageResource(R.drawable.ic_radio_checked)
            mon = !mon
        }
        binding.tuesdayBtn.setOnClickListener {
            if (tue)
                binding.tuesdayBtn.setImageResource(R.drawable.ic_radio_uncheck)
            else
                binding.tuesdayBtn.setImageResource(R.drawable.ic_radio_checked)
            tue = !tue
        }
        binding.wednesdayBtn.setOnClickListener {
            if (wed)
                binding.wednesdayBtn.setImageResource(R.drawable.ic_radio_uncheck)
            else
                binding.wednesdayBtn.setImageResource(R.drawable.ic_radio_checked)
            wed = !wed
        }
        binding.thursdayBtn.setOnClickListener {
            if (thu)
                binding.thursdayBtn.setImageResource(R.drawable.ic_radio_uncheck)
            else
                binding.thursdayBtn.setImageResource(R.drawable.ic_radio_checked)
            thu = !thu
        }
        binding.fridayBtn.setOnClickListener {
            if (fri)
                binding.fridayBtn.setImageResource(R.drawable.ic_radio_uncheck)
            else
                binding.fridayBtn.setImageResource(R.drawable.ic_radio_checked)
            fri = !fri
        }
        binding.saturdayBtn.setOnClickListener {
            if (sat)
                binding.saturdayBtn.setImageResource(R.drawable.ic_radio_uncheck)
            else
                binding.saturdayBtn.setImageResource(R.drawable.ic_radio_checked)
            sat = !sat
        }
        binding.sundayBtn.setOnClickListener {
            if (sun)
                binding.sundayBtn.setImageResource(R.drawable.ic_radio_uncheck)
            else
                binding.sundayBtn.setImageResource(R.drawable.ic_radio_checked)
            sun = !sun
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.updateDay(mon, tue, wed, thu, fri, sat, sun, routine.routineId!!)
    }

}