package kr.co.iotree.todolist.viewholder

import android.annotation.SuppressLint
import android.util.Log
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
    private lateinit var adapter: CalendarAdapter

    private var list = List(getMaxDate(year, month) + getFirstDayOfTheWeek(year, month)) { it }

    fun bindData() {
        setClickListener()

        binding.yearMonth.text = getToday("yyyy년 MM월")

        adapter = CalendarAdapter(this) //처음엔 월별달력
        adapter.setMonthList(year, month)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.itemAnimator!!.changeDuration = 0 //애니메이션 삭제
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setClickListener() {
        binding.prev.setOnClickListener {
            if (isMonth) {
                val time = getPrevMonth(year, month, date)
                setTime(time)

                binding.yearMonth.text = "${year}년 ${month}월"
                adapter.setMonthList(year, month)
                adapter.notifyDataSetChanged()
            } else { //week
                val time = getPrevWeek(year, month, date)
                setTime(time)

                binding.yearMonth.text = "${year}년 ${month}월"
                adapter.setWeekList(year, month, date)
                adapter.notifyItemRangeChanged(0, list.size)
            }
        }

        binding.next.setOnClickListener {
            if (isMonth) {
                val time = getNextMonth(year, month, date)
                setTime(time)

                binding.yearMonth.text = "${year}년 ${month}월"
                adapter.setMonthList(year, month)
                adapter.notifyDataSetChanged()
            } else {
                val time = getNextWeek(year, month, date)
                setTime(time)
                Log.d("☆☆☆☆☆☆☆☆☆☆☆☆☆☆", "setClickListener: $date")

                binding.yearMonth.text = "${year}년 ${month}월"

                Log.d("☆☆☆☆☆☆☆☆☆☆☆☆☆☆", "setClickListener: ${getMaxDate(year, getMondayMonth(year, month, date))}")
                adapter.setWeekList(year, month, date)
                adapter.notifyItemRangeChanged(0, list.size)
            }
        }

        binding.monthWeekContainer.setOnClickListener {
            if (isMonth) {
                binding.arrow.setImageResource(R.drawable.ic_calender_down)
                binding.monthWeek.text = "주"
                isMonth = !isMonth

                adapter.setWeekList(year, month, date)
                adapter.changeType()
                binding.yearMonth.text = "${year}년 ${month}월"
                adapter.notifyDataSetChanged()
            } else {
                binding.arrow.setImageResource(R.drawable.ic_calender_up)
                binding.monthWeek.text = "월"
                isMonth = !isMonth

                adapter.setMonthList(year, month)
                adapter.changeType()
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setTime(time: String) {
        year = getYearMonthDate(time, "year")
        month = getYearMonthDate(time, "month")
        date = getYearMonthDate(time, "date")
    }
}
