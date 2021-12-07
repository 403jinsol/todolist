package kr.co.iotree.todolist.viewholder

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.databinding.ViewholderCalendarDateBinding
import kr.co.iotree.todolist.util.OnItemClick

class CalendarDateViewHolder(private val binding: ViewholderCalendarDateBinding, private val holder: CalendarViewHolder) : RecyclerView.ViewHolder(binding.root) {
    fun bindMonthData(dayOfWeek: Int, listener: OnItemClick) {
        itemView.setOnClickListener {
            holder.date = binding.icon.tag.toString().toInt()
            listener.onCalendarClick(holder.year, holder.month, holder.date, true)
        }

        if (adapterPosition < dayOfWeek) { // 1일 시작하기 전이면 안보이게
            binding.icon.visibility = View.INVISIBLE
            binding.text.visibility = View.INVISIBLE
        } else {
            binding.text.text = (adapterPosition - dayOfWeek + 1).toString()
            binding.icon.tag = binding.text.text

            binding.text.setTextColor(ContextCompat.getColor(itemView.context, R.color.calendar_font_default))
            binding.text.paintFlags = 0

            if (binding.icon.tag.toString().toInt() == holder.date) {
                binding.text.setTextColor(Color.parseColor("#FF000000"))
                binding.text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }

    fun bindWeekData(date: Int, maxDate: Int, listener: OnItemClick) {
        itemView.setOnClickListener {
            if (holder.date < binding.icon.tag.toString().toInt() - 10) { //현재 날짜보다 선택한 날짜가 10일 이상 더 크면 지난달
                holder.month--
                if (holder.month <= 0) { //작년으로 넘어가면 년월 다시 설정하기
                    holder.year--
                    holder.month = 12
                }
            } else if (holder.date > binding.icon.tag.toString().toInt() + 10) { //현재 날짜보다 선택한 날짜가 10일 이상 작으면 다음달
                holder.month++
                if (holder.month >= 13) { // 내년으로 넘어가면
                    holder.year++
                    holder.month = 1
                }
            }

            holder.date = binding.icon.tag.toString().toInt()
            listener.onCalendarClick(holder.year, holder.month, holder.date, false)
        }

        if (date > maxDate) { //다음달로 넘어갈때
            binding.icon.tag = (date - maxDate).toString()
            binding.text.text = (date - maxDate).toString()
        } else {
            binding.icon.tag = date.toString()
            binding.text.text = date.toString()
        }

        binding.text.setTextColor(ContextCompat.getColor(itemView.context, R.color.calendar_font_default))
        binding.text.paintFlags = 0

        if (holder.date == binding.icon.tag.toString().toInt()) {
            binding.text.setTextColor(Color.parseColor("#FF000000"))
            binding.text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        }
    }
}