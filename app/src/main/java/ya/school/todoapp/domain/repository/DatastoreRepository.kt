package ya.school.todoapp.domain.repository

import java.util.Date

/**
 * Интерфейс репозитория для взаимодействия с датастором
 */
interface DatastoreRepository {
    suspend fun getRevision(): Int

    suspend fun setRevision(value: Int)

    suspend fun getLastOnlineTimestamp(): Date

    suspend fun setLastOnlineTimestamp(date: Date)
}