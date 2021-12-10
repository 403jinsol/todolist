package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarDateBinding
import kr.co.iotree.todolist.util.*
import kr.co.iotree.todolist.viewModel.CalendarViewModel
import kr.co.iotree.todolist.viewholder.CalendarDateViewHolder

class CalendarAdapter(private val viewModel: CalendarViewModel) : RecyclerView.Adapter<CalendarDateViewHolder>() {
    private var list: List<Int> = mutableListOf()
    private var dayOfWeek: Int = 0
    private var maxDate: Int = 0
    private var isMonth = viewModel.isMonth.value!!

    fun setMonthList(year: Int, month: Int) {
        this.list = List(getMaxDate(year, month) + getFirstDayOfTheWeek(year, month)) { it }
        dayOfWeek = getFirstDayOfTheWeek(year, month)
        if (!isMonth) {
            isMonth = true
        }
    }

    fun setWeekList(year: Int, month: Int, date: Int) {
        list = List(7) { it + getMondayDate(year, month, date) } //해당 날짜가 속한 주의 월요일 구해서 월요일부터 +1
        maxDate = getMaxDate(year, getMondayMonth(year, month, date))
        if (isMonth) {
            isMonth = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDateViewHolder {
        val binding = ViewholderCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarDateViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: CalendarDateViewHolder, position: Int) {
        if (isMonth)
            holder.bindMonthData(dayOfWeek, viewModel)
        else
            holder.bindWeekData(list[position], maxDate)
    }

    override fun getItemCount(): Int = list.size
}