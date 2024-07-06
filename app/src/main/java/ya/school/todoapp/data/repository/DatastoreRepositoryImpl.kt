package ya.school.todoapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import ya.school.todoapp.domain.repository.DatastoreRepository
import javax.inject.Inject

/**
 * Репозиторий для взаимодействия с датастором
 */
class DatastoreRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : DatastoreRepository {
    override suspend fun getRevision(): Int = withContext(Dispatchers.IO) {
        datastore.data.map { preferences ->
            preferences[revisionKey] ?: 0
        }.single()
    }

    override suspend fun setRevision(value: Int) {
        withContext(Dispatchers.IO) {
            datastore.edit { settings ->
                settings[revisionKey] = value
            }
        }
    }

    companion object {
        val revisionKey = intPreferencesKey("revision_key")
    }
}