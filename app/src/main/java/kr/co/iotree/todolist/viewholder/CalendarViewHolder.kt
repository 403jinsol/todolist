package kr.co.iotree.todolist.viewholder

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.util.dpToPx
import kr.co.iotree.todolist.util.getFirstDayOfTheWeek
import kr.co.iotree.todolist.util.getToday
import kr.co.iotree.todolist.util.setImageViewColor

class CalendarViewHolder(private val binding: ViewholderCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
    lateinit var clickedDate: String
    var year = getToday("yyyy").toInt()
    var month = getToday("MM").toInt()
    var isMonth = true

    fun bindData() {
        setYearMonth()
        setMonthCalendar(year, month)
        setClickListener()
    }

    private fun setYearMonth() {
        binding.yearMonth.text = getToday("yyyy년 MM월")
        clickedDate = getToday("d")
    }


    /**
     * 달력 세팅
     */
    //TODO 주 달라질 때 마진
    private fun setMonthCalendar(year: Int, month: Int) {
        val iconList = mutableListOf<ImageView>()
        val dateList = mutableListOf<TextView>()

        val calendarIconParam = LinearLayout.LayoutParams(dpToPx(itemView.context, 20F).toInt(), dpToPx(itemView.context, 20F).toInt(), 1F)
        val calendarDateParam = LinearLayout.LayoutParams(dpToPx(itemView.context, 20F).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT, 1F)

        val dayOfWeek = getFirstDayOfTheWeek(year, month)
        var dateTag = 1
        val maxDate = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> {
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    29
                else
                    28
            }
            else -> 0
        }

        //view 배열에 추가
        for (i in 0 until 42) {
            //달력 아이콘
            val icon = ImageView(itemView.context)
            icon.setImageResource(R.drawable.ic_clover)
            icon.layoutParams = calendarIconParam
            icon.apply {
                setImageViewColor(this, itemView.context, R.color.calendar_icon_default)
            }
            if (i < dayOfWeek || dateTag > maxDate) {
                icon.visibility = View.INVISIBLE
            } else {
                icon.tag = dateTag
            }
            iconList.add(icon)

            //달력 텍스트
            val text = TextView(itemView.context)
            text.text = i.toString()
            if (dateTag == clickedDate.toInt()) {
                text.setTextColor(Color.parseColor("#000000"))
                text.setTypeface(null, Typeface.BOLD)
                text.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }
            text.layoutParams = calendarDateParam
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.context.resources.getDimension(R.dimen.calendar_date_font_size))
            text.gravity = Gravity.CENTER

            if (i < dayOfWeek || dateTag > maxDate) {
                text.visibility = View.INVISIBLE
            } else {
                icon.tag = dateTag
                text.text = dateTag.toString()
                dateTag++
            }

            dateList.add(text)
        }

        //view 지우기
        binding.week1ImageContainer.removeAllViews()
        binding.week2ImageContainer.removeAllViews()
        binding.week3ImageContainer.removeAllViews()
        binding.week4ImageContainer.removeAllViews()
        binding.week5ImageContainer.removeAllViews()
        binding.week6ImageContainer.removeAllViews()

        binding.week1TextContainer.removeAllViews()
        binding.week2TextContainer.removeAllViews()
        binding.week3TextContainer.removeAllViews()
        binding.week4TextContainer.removeAllViews()
        binding.week5TextContainer.removeAllViews()
        binding.week6TextContainer.removeAllViews()

        //view 추가
        for (i in 0 until 7) {
            binding.week1ImageContainer.addView(iconList[i])
            binding.week2ImageContainer.addView(iconList[i + 7])
            binding.week3ImageContainer.addView(iconList[i + 14])
            binding.week4ImageContainer.addView(iconList[i + 21])
            if (iconList[28].visibility == View.VISIBLE)
                binding.week5ImageContainer.addView(iconList[i + 28])
            if (iconList[35].visibility == View.VISIBLE)
                binding.week6ImageContainer.addView(iconList[i + 35])

            binding.week1TextContainer.addView(dateList[i])
            binding.week2TextContainer.addView(dateList[i + 7])
            binding.week3TextContainer.addView(dateList[i + 14])
            binding.week4TextContainer.addView(dateList[i + 21])
            if (iconList[28].visibility == View.VISIBLE)
                binding.week5TextContainer.addView(dateList[i + 28])
            if (iconList[35].visibility == View.VISIBLE)
                binding.week6TextContainer.addView(dateList[i + 35])
        }
    }

    private fun setWeekCalendar(year: Int, month: Int) {

    }

    @SuppressLint("SetTextI18n")
    private fun setClickListener() {
        binding.prev.setOnClickListener {
            month--
            if (month < 1) {
                month = 12
                year--
            }

            binding.yearMonth.text = "${year}년 ${month}월"

            if (isMonth)
                setMonthCalendar(year, month)
            else
                setWeekCalendar(year, month)
        }

        binding.next.setOnClickListener {
            month++
            if (month > 12) {
                month = 1
                year++
            }

            binding.yearMonth.text = "${year}년 ${month}월"

            if (isMonth)
                setMonthCalendar(year, month)
            else
                setWeekCalendar(year, month)
        }

        binding.monthWeekContainer.setOnClickListener {
            if (isMonth) {
                binding.arrow.setImageResource(R.drawable.ic_calender_down)
                binding.monthWeek.text = "주"
                isMonth = !isMonth
            } else {
                binding.arrow.setImageResource(R.drawable.ic_calender_up)
                binding.monthWeek.text = "월"
                isMonth = !isMonth
            }
        }
    }
}
