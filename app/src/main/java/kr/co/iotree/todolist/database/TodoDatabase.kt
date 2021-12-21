package kr.co.iotree.todolist.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.iotree.todolist.util.GroupColor

@Database(entities = [Todo::class, TodoGroup::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun groupDao(): GroupDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(context).groupDao().insert(TodoGroup(null, "일반", 3, GroupColor.BLACK.color, false, 0))
                        }
                    }
                }).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }.also { INSTANCE = it }
        }
    }
}
