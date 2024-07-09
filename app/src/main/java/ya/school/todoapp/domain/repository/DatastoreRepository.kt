package ya.school.todoapp.domain.repository

/**
 * Интерфейс репозитория для взаимодействия с датастором
 */
interface DatastoreRepository {
    suspend fun getRevision(): Int

    suspend fun setRevision(value: Int)
}