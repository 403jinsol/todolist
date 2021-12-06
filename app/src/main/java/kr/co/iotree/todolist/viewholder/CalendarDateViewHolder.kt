package kr.co.iotree.todolist.viewholder

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarDateBinding
import kr.co.iotree.todolist.util.getToday

class CalendarDateViewHolder(private val binding: ViewholderCalendarDateBinding, private val host: CalendarViewHolder) : RecyclerView.ViewHolder(binding.root) {
    fun bindMonthData(dayOfWeek: Int) {
        itemView.setOnClickListener {
            host.date = binding.icon.tag.toString().toInt()
            binding.text.setTextColor(Color.parseColor("#FF000000"))
        }

        if (adapterPosition < dayOfWeek) {
            binding.icon.visibility = View.INVISIBLE
            binding.text.visibility = View.INVISIBLE
        } else {
            binding.text.text = (adapterPosition - dayOfWeek + 1).toString()
            binding.icon.tag = binding.text.text

            if (adapterPosition - dayOfWeek + 1 == host.date)
                binding.text.setTextColor(Color.parseColor("#FF000000"))
        }
    }

    fun bindWeekData(date: Int, maxDate: Int) {
        itemView.setOnClickListener {
            if (host.date > binding.icon.tag.toString().toInt()) {
                host.month--
            }

            host.date = binding.icon.tag.toString().toInt()
            binding.text.setTextColor(Color.parseColor("#FF000000"))
        }

        if (date > maxDate) { //다음달로 넘어갈때
            binding.icon.tag = (date - maxDate).toString()
            binding.text.text = (date - maxDate).toString()
        } else {
            binding.icon.tag = date.toString()
            binding.text.text = date.toString()
        }

        if (host.date.toString() == binding.text.text) {
            binding.text.setTextColor(Color.parseColor("#FF000000"))
        }
    }
}