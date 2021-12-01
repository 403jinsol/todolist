package kr.co.iotree.todolist.viewholder

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding

class CalendarViewHolder(private val binding: ViewholderCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData() {
        val iconList = ArrayList<ImageView>()
        val dateList = ArrayList<TextView>()
    }
}
