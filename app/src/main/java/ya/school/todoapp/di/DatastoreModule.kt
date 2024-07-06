package ya.school.todoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okio.Path.Companion.toPath

/**
 * Модуль для настройки датастора для хранения текущей ревизии
 */
@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {
    fun getDatastore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            migrations = emptyList(),
            produceFile = { context.filesDir.resolve(FILE_NAME).absolutePath.toPath() },
        )

    companion object {
        const val FILE_NAME = "user.preferences_pb"
    }
}