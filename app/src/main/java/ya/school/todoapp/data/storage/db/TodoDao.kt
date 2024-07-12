package ya.school.todoapp.data.storage.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.data.storage.db.entity.TodoItemEntity

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItems(items: Set<TodoItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(item: TodoItemEntity)

    @Query("SELECT * FROM TodoItemEntity")
    fun getTodoItems(): Flow<List<TodoItemEntity>>

    @Query("SELECT * FROM TodoItemEntity WHERE id = :id")
    fun getTodoItem(id: String): List<TodoItemEntity>

    @Update
    suspend fun updateTodoItem(item: TodoItemEntity)

    @Query("DELETE FROM TodoItemEntity WHERE id = :id")
    suspend fun deleteTodoItem(id: String)

    @Delete
    suspend fun deleteTodoItems(items: Set<TodoItemEntity>)

    @Query("UPDATE TodoItemEntity SET done = :done WHERE id = :id")
    suspend fun updateDoneStatus(id: String, done: Boolean)
}