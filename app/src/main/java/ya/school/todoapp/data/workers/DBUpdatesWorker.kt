package ya.school.todoapp.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ya.school.todoapp.domain.repository.NetworkRepository
import java.util.concurrent.TimeUnit

/**
 * Воркер для получения актуальных данных каждые 8 часов
 */
@HiltWorker
class DBUpdatesWorker @AssistedInject constructor(
    private val networkRepository: NetworkRepository,
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val result = networkRepository.getAllItems()
        return Result.success()
    }

    companion object {
        fun startUpdates(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<DBUpdatesWorker>(8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

            WorkManager
                .getInstance(context)
                .enqueueUniquePeriodicWork(
                    "updateDB",
                    ExistingPeriodicWorkPolicy.UPDATE,
                    request
                )
        }
    }
}