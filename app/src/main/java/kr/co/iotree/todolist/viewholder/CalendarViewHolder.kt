package kr.co.iotree.todolist.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.adapter.CalendarAdapter
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.util.*

class CalendarViewHolder(private val binding: ViewholderCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
    var date = getToday("d").toInt()
    var year = getToday("yyyy").toInt()
    var month = getToday("MM").toInt()
    var isMonth = true
    private lateinit var calendarAdapter: CalendarAdapter

    fun bindData(listener: OnItemClick, isMonth: Boolean) {
        setClickListener(listener)

        binding.yearMonth.text = getToday("yyyy년 MM월")

        calendarAdapter = CalendarAdapter(this, listener) //처음엔 월별달력
        if (isMonth)
            calendarAdapter.setMonthList(year, month)
        else
            calendarAdapter.setWeekList(year, month, date)
        binding.calendarRecyclerView.layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        binding.calendarRecyclerView.itemAnimator!!.changeDuration = 0 //애니메이션 삭제
        binding.calendarRecyclerView.adapter = calendarAdapter
    }

    private fun setClickListener(listener: OnItemClick) {
        binding.prev.setOnClickListener {
            if (isMonth) {
                val time = getPrevMonth(year, month, date)
                setTime(time)
                setList(listener, isMonth)
            } else { //week
                val time = getPrevWeek(year, month, date)
                setTime(time)
                setList(listener, isMonth)
            }
        }

        binding.next.setOnClickListener {
            if (isMonth) {
                val time = getNextMonth(year, month, date)
                setTime(time)
                setList(listener, isMonth)
            } else {
                val time = getNextWeek(year, month, date)
                setTime(time)
                setList(listener, isMonth)
            }
        }

        binding.monthWeekContainer.setOnClickListener {
            if (isMonth) {
                binding.arrow.setImageResource(R.drawable.ic_calender_down)
                binding.monthWeek.text = "주"
                isMonth = !isMonth
                setList(listener, isMonth)
            } else {
                binding.arrow.setImageResource(R.drawable.ic_calender_up)
                binding.monthWeek.text = "월"
                isMonth = !isMonth
                setList(listener, isMonth)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTime(time: String) { //년월일 바뀐 날짜로 재지정
        year = getYearMonthDate(time, "year")
        month = getYearMonthDate(time, "month")
        date = getYearMonthDate(time, "date")
        binding.yearMonth.text = "${year}년 ${month}월" //textView 변경
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setList(listener: OnItemClick, isMonth: Boolean) {
        if (isMonth) {
            calendarAdapter.setMonthList(year, month)
        } else {
            calendarAdapter.setWeekList(year, month, date)
        }
        listener.onCalendarClick(year, month, date, isMonth)
    }
}
