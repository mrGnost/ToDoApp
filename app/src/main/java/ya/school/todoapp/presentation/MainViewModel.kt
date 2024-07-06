package ya.school.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ya.school.todoapp.domain.repository.NetworkRepository
import ya.school.todoapp.domain.repository.SystemRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val systemRepository: SystemRepository
) : ViewModel() {
    fun startOnNetworkAvailableUpdates() {
        viewModelScope.launch {
            systemRepository.getNetworkUpdates().collect { networkAvailable ->
                if (networkAvailable)
                    networkRepository.getAllItems()
            }
        }
    }
}