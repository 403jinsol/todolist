package kr.co.iotree.todolist.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun dpToPx(context: Context, dp: Float): Float
        = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

fun pxToDp(context: Context, px: Float): Float
        = (px * DisplayMetrics.DENSITY_DEFAULT) / context.resources.displayMetrics.densityDpi