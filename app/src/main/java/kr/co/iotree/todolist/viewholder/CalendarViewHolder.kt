package kr.co.iotree.todolist.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.adapter.CalendarAdapter
import kr.co.iotree.todolist.adapter.TodoAdapter
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.util.*
import kr.co.iotree.todolist.vo.TodoGroupVo

class CalendarViewHolder(private val binding: ViewholderCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
    var date = getToday("d").toInt()
    var year = getToday("yyyy").toInt()
    var month = getToday("MM").toInt()
    var isMonth = true
    lateinit var calendarAdapter: CalendarAdapter
    lateinit var todoAdapter: TodoAdapter

    fun bindData(list: MutableList<TodoGroupVo>) {
        setClickListener()

        binding.yearMonth.text = getToday("yyyy년 MM월")

        calendarAdapter = CalendarAdapter(this) //처음엔 월별달력
        calendarAdapter.setMonthList(year, month)
        binding.calendarRecyclerView.layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        binding.calendarRecyclerView.itemAnimator!!.changeDuration = 0 //애니메이션 삭제
        binding.calendarRecyclerView.adapter = calendarAdapter

        todoAdapter = TodoAdapter(list, this)
        binding.todoRecyclerView.adapter = todoAdapter
        binding.todoRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setClickListener() {
        binding.prev.setOnClickListener {
            if (isMonth) {
                val time = getPrevMonth(year, month, date)
                setTime(time)

                calendarAdapter.setMonthList(year, month)
                calendarAdapter.notifyDataSetChanged()
            } else { //week
                val time = getPrevWeek(year, month, date)
                setTime(time)

                calendarAdapter.setWeekList(year, month, date)
                calendarAdapter.notifyItemRangeChanged(0, 7)
            }
        }

        binding.next.setOnClickListener {
            if (isMonth) {
                val time = getNextMonth(year, month, date)
                setTime(time)

                calendarAdapter.setMonthList(year, month)
                calendarAdapter.notifyDataSetChanged()
            } else {
                val time = getNextWeek(year, month, date)
                setTime(time)

                calendarAdapter.setWeekList(year, month, date)
                calendarAdapter.notifyItemRangeChanged(0, 7)
            }
        }

        binding.monthWeekContainer.setOnClickListener {
            if (isMonth) {
                binding.arrow.setImageResource(R.drawable.ic_calender_down)
                binding.monthWeek.text = "주"
                isMonth = !isMonth

                calendarAdapter.setWeekList(year, month, date)
                calendarAdapter.notifyDataSetChanged()
            } else {
                binding.arrow.setImageResource(R.drawable.ic_calender_up)
                binding.monthWeek.text = "월"
                isMonth = !isMonth

                calendarAdapter.setMonthList(year, month)
                calendarAdapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTime(time: String) {
        year = getYearMonthDate(time, "year")
        month = getYearMonthDate(time, "month")
        date = getYearMonthDate(time, "date")
        binding.yearMonth.text = "${year}년 ${month}월"
    }
}
