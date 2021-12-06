package kr.co.iotree.todolist.viewholder

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarDateBinding

class CalendarDateViewHolder(private val binding: ViewholderCalendarDateBinding, private val holder: CalendarViewHolder) : RecyclerView.ViewHolder(binding.root) {
    fun bindMonthData(dayOfWeek: Int) {
        itemView.setOnClickListener {
            holder.date = binding.icon.tag.toString().toInt()
            holder.calendarAdapter.notifyDataSetChanged() //TODO 속도 느림 다른걸로 교체
            holder.todoAdapter.notifyDataSetChanged()
        }

        if (adapterPosition < dayOfWeek) {
            binding.icon.visibility = View.INVISIBLE
            binding.text.visibility = View.INVISIBLE
        } else {
            binding.text.text = (adapterPosition - dayOfWeek + 1).toString()
            binding.icon.tag = binding.text.text

            if (adapterPosition - dayOfWeek + 1 == holder.date) {
                binding.text.setTextColor(Color.parseColor("#FF000000"))
                binding.text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }

    fun bindWeekData(date: Int, maxDate: Int) {
        itemView.setOnClickListener {
            if (holder.date > binding.icon.tag.toString().toInt()) {
                holder.month--
                if (holder.month <= 0) {
                    holder.month = 12
                    holder.year--
                }
            }

            holder.date = binding.icon.tag.toString().toInt()
            binding.text.setTextColor(Color.parseColor("#FF000000"))


        }

        if (date > maxDate) { //다음달로 넘어갈때
            binding.icon.tag = (date - maxDate).toString()
            binding.text.text = (date - maxDate).toString()
        } else {
            binding.icon.tag = date.toString()
            binding.text.text = date.toString()
        }

        if (holder.date.toString() == binding.text.text) {
            binding.text.setTextColor(Color.parseColor("#FF000000"))
        }
    }
}