package ya.school.todoapp.data.repository

import kotlinx.coroutines.flow.Flow
import ya.school.todoapp.data.system.ConnectivitySource
import ya.school.todoapp.domain.repository.SystemRepository
import javax.inject.Inject

class SystemRepositoryImpl @Inject constructor(
    private val source: ConnectivitySource
) : SystemRepository {
    override fun getNetworkUpdates(): Flow<Boolean> = source.isConnectedFlow
}