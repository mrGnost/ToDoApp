package ya.school.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.domain.entity.ThemeMode
import java.util.Date

/**
 * Интерфейс репозитория для взаимодействия с датастором
 */
interface DatastoreRepository {
    suspend fun getRevision(): Int

    suspend fun setRevision(value: Int)

    suspend fun getLastOnlineTimestamp(): Date

    suspend fun setLastOnlineTimestamp(date: Date)

    suspend fun getThemeModeFlow(): Flow<ThemeMode>

    suspend fun setThemeMode(mode: ThemeMode)
}