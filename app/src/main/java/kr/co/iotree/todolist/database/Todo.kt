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

data class Todo(
    @PrimaryKey(autoGenerate = true) val todoId: Long?,
    val content: String,
    val date: String,
    val complete: Boolean,
    @ColumnInfo(name = "group_id") val groupId: Long?
)