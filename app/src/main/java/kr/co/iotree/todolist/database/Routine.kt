package kr.co.iotree.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = TodoGroup::class,
        parentColumns = ["group_id"],
        childColumns = ["group_id"],
        onDelete = CASCADE
    )]
)

data class Routine(
    @PrimaryKey(autoGenerate = true) val routineId: Long?,
    val content: String,
    val startDate: Int,
    val endDate: Int,
    val sunday: Boolean,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    @ColumnInfo(name = "group_id") val groupId: Long?
)
