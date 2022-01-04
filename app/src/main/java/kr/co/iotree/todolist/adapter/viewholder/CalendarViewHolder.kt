package kr.co.iotree.todolist.adapter.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.PrefActivity.Companion.pref
import kr.co.iotree.todolist.adapter.CalendarAdapter
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.util.*
import kr.co.iotree.todolist.util.PrefUtil.Companion.START_SUNDAY
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class CalendarViewHolder(private val binding: ViewholderCalendarBinding, private val viewModel: CalendarViewModel) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var calendarAdapter: CalendarAdapter

    @SuppressLint("SetTextI18n")
    fun bindData(viewModel: CalendarViewModel, isMonth: Boolean) {

        if (pref.getPrefBool(START_SUNDAY, false)) {
            binding.calendarContainerSunday.visibility = View.VISIBLE
            binding.calendarContainerMonday.visibility = View.GONE
        } else {
            binding.calendarContainerSunday.visibility = View.GONE
            binding.calendarContainerMonday.visibility = View.VISIBLE
        }

        if (pref.getPrefString(PrefUtil.LOCALE_CODE, LocaleUtil.OPTION_PHONE_LANGUAGE) == "en") {
            val monthArray = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
            binding.yearMonth.text = String.format("%s %d", monthArray[viewModel.month.value!! - 1], viewModel.year.value)
        } else {
            binding.yearMonth.text = String.format(itemView.context.resources.getString(R.string.calendar_text), viewModel.year.value, viewModel.month.value)
        }

        calendarAdapter = CalendarAdapter(viewModel)

        binding.completeCount.text = viewModel.completeCount.value.toString()

        if (isMonth)
            calendarAdapter.setMonthList(viewModel.year.value!!, viewModel.month.value!!)
        else
            calendarAdapter.setWeekList(viewModel.year.value!!, viewModel.month.value!!, viewModel.date.value!!)

        binding.calendarRecyclerView.layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        binding.calendarRecyclerView.itemAnimator!!.changeDuration = 0 //애니메이션 삭제
        binding.calendarRecyclerView.adapter = calendarAdapter

        setClickListener(viewModel.year.value!!, viewModel.month.value!!, viewModel.date.value!!, viewModel.isMonth.value!!)
    }

    private fun setClickListener(year: Int, month: Int, date: Int, isMonth: Boolean) {
        binding.prev.setOnClickListener {
            if (isMonth) {
                setTime(getPrevMonth(year, month, date))
            } else { //week
                setTime(getPrevWeek(year, month, date))
            }
        }

        binding.next.setOnClickListener {
            if (isMonth) {
                setTime(getNextMonth(year, month, date))
            } else {
                setTime(getNextWeek(year, month, date))
            }
        }

        binding.monthWeekContainer.setOnClickListener {
            if (isMonth) {
                binding.arrow.setImageResource(R.drawable.ic_calender_down)
                binding.monthWeek.text = itemView.context.resources.getText(R.string.week)
                viewModel.isMonth.value = !isMonth
            } else {
                binding.arrow.setImageResource(R.drawable.ic_calender_up)
                binding.monthWeek.text = itemView.context.resources.getText(R.string.month)
                viewModel.isMonth.value = !isMonth
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTime(time: String) { //년월일 바뀐 날짜로 재지정
        viewModel.year.value = getYearMonthDate(time, "year")
        viewModel.month.value = getYearMonthDate(time, "month")
        viewModel.date.value = getYearMonthDate(time, "date")
    }
}
