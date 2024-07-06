package ya.school.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория, отвечающего за мониторинг состояния аспектов системы
 */
interface SystemRepository {
    fun getNetworkUpdates(): Flow<Boolean>
}