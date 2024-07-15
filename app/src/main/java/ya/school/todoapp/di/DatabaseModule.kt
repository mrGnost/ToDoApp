package ya.school.todoapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ya.school.todoapp.data.storage.db.AppDatabase
import ya.school.todoapp.data.storage.db.DatabaseScheme
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DatabaseScheme.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.todoDao()
}