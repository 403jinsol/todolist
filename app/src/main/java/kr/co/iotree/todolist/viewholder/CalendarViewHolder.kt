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
import kr.co.iotree.todolist.util.*

class CalendarViewHolder(private val binding: ViewholderCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
    var selectedDate: Int = 0
    var year = getToday("yyyy").toInt()
    var month = getToday("MM").toInt()
    var isMonth = true

    fun bindData() {
        setYearMonth()
        setMonthCalendar(year, month) //처음엔 오늘날짜로
        setClickListener()
    }

    private fun setYearMonth() {
        binding.yearMonth.text = getToday("yyyy년 MM월")
        selectedDate = getToday("d").toInt()
    }

    /**
     * 달력 세팅
     */
    private fun setMonthCalendar(year: Int, month: Int) {
        val iconList = mutableListOf<ImageView>()
        val dateList = mutableListOf<TextView>()

        val calendarIconParam = LinearLayout.LayoutParams(dpToPx(itemView.context, 20F).toInt(), dpToPx(itemView.context, 20F).toInt(), 1F)
        val calendarDateParam = LinearLayout.LayoutParams(dpToPx(itemView.context, 20F).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT, 1F)

        val dayOfWeek = getFirstDayOfTheWeek(year, month)
        var dateTag = 1
        val maxDate = getMaxDate(year, month)
        var selectedIndex = 0

        setBasicViewVisibility()

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
                icon.isEnabled = false
            } else {
                icon.tag = dateTag
            }
            icon.setOnClickListener {
                dateList[selectedIndex].setTextColor(Color.parseColor("#808080"))
                dateList[selectedIndex].setTypeface(null, Typeface.NORMAL)
                dateList[selectedIndex].paintFlags = 0

                dateList[i].setTextColor(Color.parseColor("#000000"))
                dateList[i].setTypeface(null, Typeface.BOLD)
                dateList[i].paintFlags = Paint.UNDERLINE_TEXT_FLAG

                selectedDate = it.tag.toString().toInt()
                selectedIndex = i
            }
            iconList.add(icon)

            //달력 텍스트
            val date = TextView(itemView.context)
            date.text = i.toString()
            if (dateTag == selectedDate) {
                date.setTextColor(Color.parseColor("#000000"))
                date.setTypeface(null, Typeface.BOLD)
                date.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                selectedIndex = i
            }
            date.layoutParams = calendarDateParam
            date.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.context.resources.getDimension(R.dimen.calendar_date_font_size))
            date.gravity = Gravity.CENTER

            if (i < dayOfWeek || dateTag > maxDate) {
                date.visibility = View.INVISIBLE
                date.isEnabled = false
            } else {
                date.tag = dateTag
                date.text = dateTag.toString()
                dateTag++
            }

            date.setOnClickListener {
                dateList[selectedIndex].setTextColor(Color.parseColor("#808080"))
                dateList[selectedIndex].setTypeface(null, Typeface.NORMAL)
                dateList[selectedIndex].paintFlags = 0

                (it as TextView).setTextColor(Color.parseColor("#000000"))
                it.setTypeface(null, Typeface.BOLD)
                it.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                selectedDate = it.tag.toString().toInt()
                selectedIndex = i
            }

            dateList.add(date)
        }

        addView(iconList, dateList)
    }

    private fun setWeekCalendar(year: Int, month: Int, day: Int) {
        setBasicViewVisibility()

        val iconList = mutableListOf<ImageView>()
        val dateList = mutableListOf<TextView>()

        val calendarIconParam = LinearLayout.LayoutParams(dpToPx(itemView.context, 20F).toInt(), dpToPx(itemView.context, 20F).toInt(), 1F)
        val calendarDateParam = LinearLayout.LayoutParams(dpToPx(itemView.context, 20F).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT, 1F)

        var selectedIndex = 0

        for (i in 0 until 7) {
            //달력 텍스트
            val date = TextView(itemView.context)
            date.layoutParams = calendarDateParam
            date.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.context.resources.getDimension(R.dimen.calendar_date_font_size))
            date.gravity = Gravity.CENTER

            // 기준 날짜(day)와 일수 차이
            val diff = i - getDayOfTheWeek(year, month, day)

            //날짜 요일 맞춰서 표시
            when {
                i == getDayOfTheWeek(year, month, day) -> { //현재 날짜와 같으면
                    date.text = day.toString()
                    date.tag = day

                    date.setTextColor(Color.parseColor("#000000"))
                    date.setTypeface(null, Typeface.BOLD)
                    date.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                    selectedIndex = i
                }
                i < getDayOfTheWeek(year, month, day) -> { //현재 날짜보다 빠르면
                    if ((day + diff) < 1) { //이전달 나오면
                        date.text = (getMaxDate(year, month - 1) + (day - (getDayOfTheWeek(year, month, day) - i))).toString()
                        date.tag = (getMaxDate(year, month - 1) + (day - (getDayOfTheWeek(year, month, day) - i))).toString()
                    } else {
                        date.text = (day - (getDayOfTheWeek(year, month, day) - i)).toString()
                        date.tag = (day - (getDayOfTheWeek(year, month, day) - i)).toString()
                    }
                }
                else -> { //현재 날짜보다 느리면
                    if ((day + diff) > getMaxDate(year, month)) { //다음달 나오면
                        date.text = ((day + (i - (getDayOfTheWeek(year, month, day)))) - getMaxDate(year, month)).toString()
                        date.tag = ((day + (i - (getDayOfTheWeek(year, month, day)))) - getMaxDate(year, month)).toString()
                    } else {
                        date.text = (day + (i - (getDayOfTheWeek(year, month, day)))).toString()
                        date.tag = (day + (i - (getDayOfTheWeek(year, month, day)))).toString()
                    }
                }
            }

            date.setOnClickListener {
                dateList[selectedIndex].setTextColor(Color.parseColor("#808080"))
                dateList[selectedIndex].setTypeface(null, Typeface.NORMAL)
                dateList[selectedIndex].paintFlags = 0

                (it as TextView).setTextColor(Color.parseColor("#000000"))
                it.setTypeface(null, Typeface.BOLD)
                it.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                selectedDate = it.tag.toString().toInt()
                selectedIndex = i
            }

            dateList.add(date)

            //달력 아이콘
            val icon = ImageView(itemView.context)
            icon.setImageResource(R.drawable.ic_clover)
            icon.layoutParams = calendarIconParam
            icon.apply {
                setImageViewColor(this, itemView.context, R.color.calendar_icon_default)
            }

            icon.tag = day

            icon.setOnClickListener {
                dateList[selectedIndex].setTextColor(Color.parseColor("#808080"))
                dateList[selectedIndex].setTypeface(null, Typeface.NORMAL)
                dateList[selectedIndex].paintFlags = 0

                dateList[i].setTextColor(Color.parseColor("#000000"))
                dateList[i].setTypeface(null, Typeface.BOLD)
                dateList[i].paintFlags = Paint.UNDERLINE_TEXT_FLAG

                selectedDate = it.tag.toString().toInt()
                selectedIndex = i
            }
            iconList.add(icon)
        }

        addView(iconList, dateList)
    }

    private fun setBasicViewVisibility() {
        if (isMonth) { //month 모든 줄 보이게
            binding.week2ImageContainer.visibility = View.VISIBLE
            binding.week2TextContainer.visibility = View.VISIBLE
            binding.week3ImageContainer.visibility = View.VISIBLE
            binding.week3TextContainer.visibility = View.VISIBLE
            binding.week4ImageContainer.visibility = View.VISIBLE
            binding.week4TextContainer.visibility = View.VISIBLE
            binding.week5ImageContainer.visibility = View.VISIBLE
            binding.week5TextContainer.visibility = View.VISIBLE
            binding.week6ImageContainer.visibility = View.VISIBLE
            binding.week6TextContainer.visibility = View.VISIBLE

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
        } else { //week 한줄만
            binding.week2ImageContainer.visibility = View.GONE
            binding.week2TextContainer.visibility = View.GONE
            binding.week3ImageContainer.visibility = View.GONE
            binding.week3TextContainer.visibility = View.GONE
            binding.week4ImageContainer.visibility = View.GONE
            binding.week4TextContainer.visibility = View.GONE
            binding.week5ImageContainer.visibility = View.GONE
            binding.week5TextContainer.visibility = View.GONE
            binding.week6ImageContainer.visibility = View.GONE
            binding.week6TextContainer.visibility = View.GONE

            binding.week1ImageContainer.removeAllViews()
            binding.week1TextContainer.removeAllViews()
        }
    }

    private fun addView(iconList: MutableList<ImageView>, dateList: MutableList<TextView>) {
        if (isMonth) {
            for (i in 0 until 7) {
                binding.week1ImageContainer.addView(iconList[i])
                binding.week2ImageContainer.addView(iconList[i + 7])
                binding.week3ImageContainer.addView(iconList[i + 14])
                binding.week4ImageContainer.addView(iconList[i + 21])
                if (iconList[28].visibility == View.VISIBLE) {
                    binding.week5ImageContainer.visibility = View.VISIBLE
                    binding.week5ImageContainer.addView(iconList[i + 28])
                } else {
                    binding.week5ImageContainer.visibility = View.GONE
                }
                if (iconList[35].visibility == View.VISIBLE) {
                    binding.week6ImageContainer.visibility = View.VISIBLE
                    binding.week6ImageContainer.addView(iconList[i + 35])
                } else {
                    binding.week6ImageContainer.visibility = View.GONE
                }

                binding.week1TextContainer.addView(dateList[i])
                binding.week2TextContainer.addView(dateList[i + 7])
                binding.week3TextContainer.addView(dateList[i + 14])
                binding.week4TextContainer.addView(dateList[i + 21])
                if (iconList[28].visibility == View.VISIBLE) {
                    binding.week5TextContainer.visibility = View.VISIBLE
                    binding.week5TextContainer.addView(dateList[i + 28])
                } else {
                    binding.week5TextContainer.visibility = View.GONE
                }
                if (iconList[35].visibility == View.VISIBLE) {

                    binding.week6TextContainer.visibility = View.VISIBLE
                    binding.week6TextContainer.addView(dateList[i + 35])
                } else {
                    binding.week6TextContainer.visibility = View.GONE
                }
            }
        } else {
            for (i in 0 until 7) {
                binding.week1ImageContainer.addView(iconList[i])
                binding.week1TextContainer.addView(dateList[i])
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setClickListener() {
        binding.prev.setOnClickListener {
            if (isMonth) {
                month--
                if (month < 1) {
                    month = 12
                    year--
                }

                setMonthCalendar(year, month)
                binding.yearMonth.text = "${year}년 ${month}월"
            } else { //week
                selectedDate -= 7
                if (selectedDate < 1) {
                    month--
                    if (month < 1) {
                        month = 12
                        year--
                    }

                    selectedDate += getMaxDate(year, month)
                }
                setWeekCalendar(year, month, selectedDate)
                binding.yearMonth.text = "${year}년 ${month}월"
            }
        }

        binding.next.setOnClickListener {
            if (isMonth) {
                month++
                if (month > 12) {
                    month = 1
                    year++
                }

                setMonthCalendar(year, month)
                binding.yearMonth.text = "${year}년 ${month}월"
            } else {
                selectedDate += 7
                if (selectedDate > getMaxDate(year, month)) {
                    selectedDate -= getMaxDate(year, month)
                    month++
                    if (month > 12) {
                        month = 1
                        year++
                    }
                }
                setWeekCalendar(year, month, selectedDate)
                binding.yearMonth.text = "${year}년 ${month}월"
            }
        }

        binding.monthWeekContainer.setOnClickListener {
            if (isMonth) {
                binding.arrow.setImageResource(R.drawable.ic_calender_down)
                binding.monthWeek.text = "주"
                isMonth = !isMonth
                setWeekCalendar(year, month, selectedDate)
            } else {
                binding.arrow.setImageResource(R.drawable.ic_calender_up)
                binding.monthWeek.text = "월"
                isMonth = !isMonth
                setMonthCalendar(year, month)
            }
        }
    }
}
