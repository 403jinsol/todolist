package kr.co.iotree.todolist.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun dpToPx(context: Context, dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

fun pxToDp(context: Context, px: Float): Float = (px * DisplayMetrics.DENSITY_DEFAULT) / context.resources.displayMetrics.densityDpi

fun getToday(pattern: String): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val format = DateTimeFormatter.ofPattern(pattern)
        LocalDate.now().format(format)
    } else {
        @SuppressLint("SimpleDateFormat")
        val format = SimpleDateFormat(pattern)
        format.format(Date(System.currentTimeMillis()))
    }
}

/**
 * 매달 1일의 요일 계산
 * 월요일이 0, 일요일이 7
 */
fun getFirstDayOfTheWeek(year: Int, month: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(year, month - 1, 1)
    return if (cal[Calendar.DAY_OF_WEEK] == 1)
        6
    else
        cal[Calendar.DAY_OF_WEEK] - 2
}

/**
 * 요일 계산
 * 월요일이 0, 일요일이 7
 */
fun getDayOfTheWeek(year: Int, month: Int, day: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(year, month - 1, day)
    return if (cal[Calendar.DAY_OF_WEEK] == 1)
        6
    else
        cal[Calendar.DAY_OF_WEEK] - 2
}

fun setImageViewColor(imageView: ImageView, context: Context, color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        imageView.setColorFilter(context.getColor(color))
    } else {
        imageView.setColorFilter(context.resources.getColor(color))
    }
}

/**
 * 달의 마지막날 숫자 가져옴
 */
fun getMaxDate(year: Int, month: Int): Int {
    return when (month) {
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
}