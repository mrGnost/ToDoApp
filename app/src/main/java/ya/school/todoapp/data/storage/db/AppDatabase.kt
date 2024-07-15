package ya.school.todoapp.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ya.school.todoapp.data.storage.db.entity.TodoItemEntity

@Database(
    entities = [TodoItemEntity::class],
    version = DatabaseScheme.DB_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}