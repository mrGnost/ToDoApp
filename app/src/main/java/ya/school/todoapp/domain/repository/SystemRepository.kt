package ya.school.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SystemRepository {
    fun getNetworkUpdates(): Flow<Boolean>
}