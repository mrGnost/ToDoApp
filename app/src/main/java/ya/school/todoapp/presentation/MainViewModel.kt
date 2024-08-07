package ya.school.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ya.school.todoapp.domain.entity.ThemeMode
import ya.school.todoapp.domain.repository.DatastoreRepository
import ya.school.todoapp.domain.repository.SystemRepository
import ya.school.todoapp.domain.usecase.UpdateItemsUseCase
import javax.inject.Inject

/**
 * Вьюмодель главной активности
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val systemRepository: SystemRepository,
    private val datastoreRepository: DatastoreRepository,
    private val updateItemsUseCase: UpdateItemsUseCase
) : ViewModel() {
    private val themeMutable = MutableStateFlow(ThemeMode.System)
    private val theme: StateFlow<ThemeMode> = themeMutable

    init {
        fetchTheme()
    }

    fun getThemeFlow(): StateFlow<ThemeMode> = theme

    fun startOnNetworkAvailableUpdates() {
        viewModelScope.launch {
            systemRepository.getNetworkUpdates().collect { networkAvailable ->
                if (networkAvailable)
                    updateItemsUseCase()
            }
        }
    }

    private fun fetchTheme() {
        viewModelScope.launch {
            datastoreRepository.getThemeModeFlow().collect {
                themeMutable.emit(it)
            }
        }
    }
}