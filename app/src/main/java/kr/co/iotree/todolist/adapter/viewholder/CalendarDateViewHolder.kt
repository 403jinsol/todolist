package kr.co.iotree.todolist.adapter.viewholder

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.ViewholderCalendarDateBinding
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class CalendarDateViewHolder(private val binding: ViewholderCalendarDateBinding, private val viewModel: CalendarViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bindMonthData(dayOfWeek: Int, viewModel: CalendarViewModel) {
        itemView.setOnClickListener {
            viewModel.date.value = binding.dateIcon.tag.toString().toInt()
        }

        if (adapterPosition < dayOfWeek) { // 1일 시작하기 전이면 안보이게
            binding.dateIcon.visibility = View.INVISIBLE
            binding.text.visibility = View.INVISIBLE
        } else {
            binding.text.text = (adapterPosition - dayOfWeek + 1).toString()
            binding.dateIcon.tag = binding.text.text

            if (binding.dateIcon.tag.toString().toInt() == viewModel.date.value!!) {
                binding.text.setTextColor(Color.parseColor("#FF000000"))
                binding.underline.visibility = View.VISIBLE
            }
        }
        setIconNumber()
    }

    fun bindWeekData(date: Int, maxDate: Int) {
        itemView.setOnClickListener {
            var year = viewModel.year.value!!
            var month = viewModel.month.value!!
            val curDate = viewModel.date.value!!

            if (curDate < date - 10) { //현재 날짜보다 선택한 날짜가 10일 이상 더 크면 지난달
                month--
                if (month <= 0) { //작년으로 넘어가면 년월 다시 설정하기
                    year--
                    month = 12
                }
            } else if (date > binding.dateIcon.tag.toString().toInt() + 10) { //현재 날짜보다 선택한 날짜가 10일 이상 작으면 다음달
                month++
                if (month >= 13) { // 내년으로 넘어가면
                    year++
                    month = 1
                }
            }

            viewModel.year.value = year
            viewModel.month.value = month
            viewModel.date.value = binding.dateIcon.tag.toString().toInt()
        }

        if (date > maxDate) { //다음달로 넘어갈때
            binding.dateIcon.tag = (date - maxDate).toString()
            binding.text.text = (date - maxDate).toString()
        } else {
            binding.dateIcon.tag = date.toString()
            binding.text.text = date.toString()
        }

        binding.text.setTextColor(ContextCompat.getColor(itemView.context, R.color.calendar_font_default))
        binding.text.paintFlags = 0

        if (binding.dateIcon.tag.toString().toInt() == viewModel.date.value!!) {
            binding.text.setTextColor(Color.parseColor("#FF000000"))
            binding.underline.visibility = View.VISIBLE
        }

        setIconNumber()
    }

    private fun setIconNumber() {
        val db = TodoDatabase.getInstance(itemView.context, null)
        val allTodo = db.todoDao().getAllDayTodo("${viewModel.year.value}${viewModel.month.value}${binding.text.text}".toInt())
        val allCompleteTodo = db.todoDao().getCompleteTodo("${viewModel.year.value}${viewModel.month.value}${binding.text.text}".toInt(), true)

        binding.number.text = ((allTodo.size) - (allCompleteTodo.size)).toString()

        if (allTodo.size == 0) {
            binding.number.visibility = View.INVISIBLE
        }

        if (allTodo.size != 0 && (allTodo.size - allCompleteTodo.size) == 0) {
            binding.dateIcon.setColorFilter(db.groupDao().getGroup(allTodo[0].groupId)!!.color)
            binding.number.visibility = View.INVISIBLE
            binding.checkIcon.visibility = View.VISIBLE
        }
    }
}