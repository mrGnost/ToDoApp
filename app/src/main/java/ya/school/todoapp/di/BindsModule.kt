package ya.school.todoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ya.school.todoapp.data.repository.DatastoreRepositoryImpl
import ya.school.todoapp.data.repository.NetworkRepositoryImpl
import ya.school.todoapp.data.repository.SystemRepositoryImpl
import ya.school.todoapp.data.repository.TodoItemsRepositoryImpl
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.SystemRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository

/**
 * Модуль, связывающий интерфейсы репозиториев с их реализациями
 */
@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun bindTodoRepository(repository: TodoItemsRepositoryImpl): TodoItemsRepository

    @Binds
    fun bindNetworkRepository(repository: NetworkRepositoryImpl): NetworkRepository

    @Binds
    fun bindSystemRepository(repository: SystemRepositoryImpl): SystemRepository

    @Binds
    fun bindDatastoreRepository(repository: DatastoreRepositoryImpl): DatastoreRepository
}