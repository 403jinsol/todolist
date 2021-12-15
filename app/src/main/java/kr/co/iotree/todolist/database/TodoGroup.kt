package kr.co.iotree.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoGroup(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "group_id") val groupId: Long?,
    val title: String,
    val groupPublic: Int,
    val color: Int,
    val complete: Boolean,
    val reason: Int
)