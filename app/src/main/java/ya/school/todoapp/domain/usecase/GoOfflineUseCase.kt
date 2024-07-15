package ya.school.todoapp.domain.usecase

import ya.school.todoapp.domain.repository.DatastoreRepository
import java.util.Date
import javax.inject.Inject

class GoOfflineUseCase @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke() {
        datastoreRepository.setLastOnlineTimestamp(Date())
    }
}