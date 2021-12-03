package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarDateBinding
import kr.co.iotree.todolist.util.getFirstDayOfTheWeek
import kr.co.iotree.todolist.util.getMaxDate
import kr.co.iotree.todolist.util.getMondayDate
import kr.co.iotree.todolist.util.getMondayMonth
import kr.co.iotree.todolist.viewholder.CalendarDateViewHolder
import kr.co.iotree.todolist.viewholder.CalendarViewHolder

class CalendarAdapter(private val host: CalendarViewHolder) : RecyclerView.Adapter<CalendarDateViewHolder>() {
    private var list: List<Int> = mutableListOf()
    private var dayOfWeek: Int = 0
    private var maxDate: Int = 0
    private var isMonth = host.isMonth //TODO 이거 정리

    fun setMonthList(year: Int, month: Int) {
        this.list = List(getMaxDate(year, month) + getFirstDayOfTheWeek(year, month)) { it }
        dayOfWeek = getFirstDayOfTheWeek(year, month)
        if (!isMonth) {
            isMonth = true
        }
    }

    fun setWeekList(year: Int, month: Int, date: Int) {
        list = List(7) { it + getMondayDate(year, month, date) }
        maxDate = getMaxDate(year, getMondayMonth(year, month, date))
        if (isMonth) {
            isMonth = false
        }
    }

    fun changeType() {
        isMonth = !isMonth
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDateViewHolder {
        val binding = ViewholderCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarDateViewHolder(binding, host)
    }

    override fun onBindViewHolder(holder: CalendarDateViewHolder, position: Int) {
        if (isMonth)
            holder.bindMonthData(dayOfWeek)
        else
            holder.bindWeekData(list[position], maxDate)
    }

    override fun getItemCount(): Int = list.size
}