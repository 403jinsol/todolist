package kr.co.iotree.todolist.viewholder

import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.adapter.MainAdapter
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding

class CalendarViewHolder(private val binding: ViewholderCalendarBinding, val host:MainAdapter) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(){
        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = year.toString()+month.toString()+dayOfMonth.toString()
        }
    }
}