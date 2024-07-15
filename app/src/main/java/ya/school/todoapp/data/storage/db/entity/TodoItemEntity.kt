package ya.school.todoapp.data.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ya.school.todoapp.domain.entity.TodoItem
import java.util.Date

@Entity
data class TodoItemEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "importance") val importance: TodoItem.Importance,
    @ColumnInfo(name = "deadline") val deadline: Date? = null,
    @ColumnInfo(name = "done") val done: Boolean,
    @ColumnInfo(name = "color") val color: String? = null,
    @ColumnInfo(name = "file_paths") val filePaths: List<String>? = null,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "changed_at") val changedAt: Date,
    @ColumnInfo(name = "last_updated_by") val lastUpdatedBy: String
)
