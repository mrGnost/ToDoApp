package ya.school.todoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ya.school.todoapp.data.TodoItemsRepositoryImpl
import ya.school.todoapp.domain.repository.TodoItemsRepository

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun bindTodoRepository(repository: TodoItemsRepositoryImpl): TodoItemsRepository
}