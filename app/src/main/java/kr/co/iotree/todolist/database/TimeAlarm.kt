package kr.co.iotree.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimeAlarm(
    @PrimaryKey val timeAlarmId: Long?,
    val allTime:Int,
    val hour: Int,
    val minute: Int
)