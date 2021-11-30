package kr.co.iotree.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var content: String,
    var todoGroupName: String,
    var date: String,
    var complete: Boolean
)