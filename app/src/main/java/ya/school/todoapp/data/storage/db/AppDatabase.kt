package ya.school.todoapp.data.storage.db

import androidx.room.RoomDatabase

abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}