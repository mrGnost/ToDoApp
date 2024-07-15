package ya.school.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.SystemRepository
import ya.school.todoapp.domain.repository.TodoItemsRepository
import ya.school.todoapp.domain.usecase.UpdateItemsUseCase
import javax.inject.Inject

/**
 * Вьюмодель главной активности
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val systemRepository: SystemRepository,
    private val updateItemsUseCase: UpdateItemsUseCase
) : ViewModel() {
    fun startOnNetworkAvailableUpdates() {
        viewModelScope.launch {
            systemRepository.getNetworkUpdates().collect { networkAvailable ->
                if (networkAvailable)
                    updateItemsUseCase()
            }
        }
    }
}