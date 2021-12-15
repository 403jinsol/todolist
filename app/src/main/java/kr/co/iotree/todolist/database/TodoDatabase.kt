package kr.co.iotree.todolist.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kr.co.iotree.todolist.util.GroupColor

@Database(entities = [Todo::class, TodoGroup::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun groupDao(): GroupDao

    companion object {
        private var instance: TodoDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TodoDatabase? {
            if (instance == null) {
                synchronized(TodoDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java, "todo-database"
                    ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            AsyncTask.execute {
                                getInstance(context)!!.groupDao().insert(TodoGroup(null, "일반", 3, GroupColor.BLACK.color, false, 0))
                            }
                        }
                    })
                        .allowMainThreadQueries() // FIXME : 일반적으로 mainThread 에서 사용하지 않음
                        .build()
                }
            }
            return instance
        }
    }
}
