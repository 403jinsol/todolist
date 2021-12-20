package kr.co.iotree.todolist.util

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*

fun dpToPx(context: Context, dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

/**
 * 오늘 날짜 가져오기
 */
fun getToday(pattern: String): Int = SimpleDateFormat(pattern, Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()

/**
 * 지난달로 이동
 * String yyyyMMd 반환하니까 getYearMonth()로 잘라서 쓰기
 */
fun getPrevMonth(year: Int, month: Int, date: Int): String {
    val df = SimpleDateFormat("yyyyMMd", Locale.getDefault())

    val cal = Calendar.getInstance()
    cal.set(year, (month - 1) - 1, date)
    return df.format(cal.time)
}

/**
 * 다음달로 이동
 * String yyyyMMd 반환하니까 getYearMonth()로 잘라서 쓰기
 */
fun getNextMonth(year: Int, month: Int, date: Int): String {
    val df = SimpleDateFormat("yyyyMMd", Locale.getDefault())

    val cal = Calendar.getInstance()
    cal.set(year, (month - 1) + 1, date)
    return df.format(cal.time)
}

/**
 * 지난주로 이동
 * String yyyyMMd 반환하니까 getYearMonth()로 잘라서 쓰기
 */
fun getPrevWeek(year: Int, month: Int, date: Int): String {
    val df = SimpleDateFormat("yyyyMMd", Locale.getDefault())

    val cal = Calendar.getInstance()
    cal.set(year, month - 1, date)

    if (date - getDayOfTheWeek(year, month, date) < 1) { // 지난달로 넘어갈 때 달이 안바뀌어서 직접 바꿔줘야함
        if ((month - 1) - 1 < 0) { // 작년으로 넘어가는 것도 직접 설정
            cal.set(year - 1, 11, getMaxDate(year, 12) - 7 + date)
        } else {
            cal.set(year, (month - 1) - 1, getMaxDate(year, month - 1) - 7 + date)
        }
    } else {
        cal.add(Calendar.DATE, -7)
    }
    return df.format(cal.time)
}

/**
 * 다음주로 이동
 * String yyyyMMd 반환하니까 getYearMonth()로 잘라서 쓰기
 */
fun getNextWeek(year: Int, month: Int, date: Int): String {
    val df = SimpleDateFormat("yyyyMMd", Locale.getDefault())

    val cal = Calendar.getInstance()
    cal.set(year, month - 1, date)

    cal.add(Calendar.DATE, 7)
    return df.format(cal.time)
}

/**
 * 내일로 이동
 * String yyyyMMd 반환하니까 getYearMonth()로 잘라서 쓰기
 */
fun getNextDay(time:Int): String {
    val df = SimpleDateFormat("yyyyMMd", Locale.getDefault())

    val year = time.toString().substring(0,4).toInt()
    val month = time.toString().substring(4,6).toInt()
    val date = time.toString().substring(6).toInt()

    val cal = Calendar.getInstance()
    cal.set(year, month - 1, date)

    cal.add(Calendar.DATE, 1)
    return df.format(cal.time)
}

fun getYearMonthDate(time: String, type: String): Int {
    return when (type) {
        "year" -> time.substring(0, 4).toInt()
        "month" -> time.substring(4, 6).toInt()
        "date" -> time.substring(6).toInt()
        else -> 0
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
fun getDayOfTheWeek(year: Int, month: Int, date: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(year, month - 1, date)
    return if (cal[Calendar.DAY_OF_WEEK] == 1)
        6
    else
        cal[Calendar.DAY_OF_WEEK] - 2
}

/**
 * 해당 날짜가 들어있는 주의 월요일 찾기
 * 일자만 반환
 */
fun getMondayDate(year: Int, month: Int, date: Int): Int {
    val df = SimpleDateFormat("d", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.set(year, month - 1, date)

    if (date - getDayOfTheWeek(year, month, date) < 1) { //지난달로 넘어가면
        if ((month - 1) - 1 < 0) { //작년으로 넘어가면
            cal.set(year - 1, 11, getMaxDate(year, 12) - (getDayOfTheWeek(year, month, date) - date))
        } else {
            cal.set(year, (month - 1) - 1, getMaxDate(year, month - 1) - (getDayOfTheWeek(year, month, date) - date))
        }
    } else {
        cal.add(Calendar.DATE, -getDayOfTheWeek(year, month, date))
    }
    return df.format(cal.time).toInt()
}

/**
 * 월요일 찾기
 * 월만 반환
 */
fun getMondayMonth(year: Int, month: Int, date: Int): Int {
    val df = SimpleDateFormat("MM", Locale.getDefault())
    val cal = Calendar.getInstance()
    cal.set(year, month - 1, date)

    if (date - getDayOfTheWeek(year, month, date) < 1) {
        if ((month - 1) - 1 < 0) {
            cal.set(year - 1, 11, getMaxDate(year, 12) - (getDayOfTheWeek(year, month, date) - date))
        } else {
            cal.set(year, (month - 1) - 1, getMaxDate(year, month - 1) - (getDayOfTheWeek(year, month, date) - date))
        }
    } else {
        cal.add(Calendar.DATE, -getDayOfTheWeek(year, month, date))
    }
    return df.format(cal.time).toInt()
}

/**
 * 달의 마지막날 숫자 가져옴
 * 달은 -1 할 필요 없이 그대로 적으면 됨
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

fun setImageViewColor(imageView: ImageView, context: Context, color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        imageView.setColorFilter(context.getColor(color))
    } else {
        imageView.setColorFilter(context.resources.getColor(color))
    }
}