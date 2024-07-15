package ya.school.todoapp.data.storage.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import ya.school.todoapp.domain.entity.TodoItem
import java.util.Date

object Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun importanceToString(importance: TodoItem.Importance): String {
        return importance.toString()
    }

    @TypeConverter
    fun importanceFromString(value: String): TodoItem.Importance {
        return TodoItem.Importance.valueOf(value)
    }

    @TypeConverter
    fun stringListToJson(list: List<String>?): String = gson.toJson(list)

    @TypeConverter
    fun stringListFromJson(json: String): List<String>? {
        return gson.fromJson(json, Array<String>::class.java)?.toList()
    }
}