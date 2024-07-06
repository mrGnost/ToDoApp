package ya.school.todoapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import ya.school.todoapp.data.workers.DBUpdatesWorker
import javax.inject.Inject

/**
 * Класс приложения
 */
@HiltAndroidApp
class ToDoApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory : HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        DBUpdatesWorker.startUpdates(this)
    }
}