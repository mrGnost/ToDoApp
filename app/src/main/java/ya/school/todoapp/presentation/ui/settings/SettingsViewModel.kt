package ya.school.todoapp.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ya.school.todoapp.domain.entity.ThemeMode
import ya.school.todoapp.domain.repository.DatastoreRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {
    private val themeMutable = MutableStateFlow(ThemeMode.System)
    private val theme: StateFlow<ThemeMode> = themeMutable

    init {
        fetchTheme()
    }

    fun getThemeFlow(): StateFlow<ThemeMode> = theme

    fun setTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            datastoreRepository.setThemeMode(themeMode)
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