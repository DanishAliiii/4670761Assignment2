package au.vu.nit3213.a4670761.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.vu.nit3213.a4670761.data.repo.DashboardRepository
import au.vu.nit3213.a4670761.domain.model.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

data class DashboardState(
    val loading: Boolean = false,
    val items: List<Entity> = emptyList(),
    val total: Int = 0,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DashboardRepository
) : ViewModel() {

    private val _state = MutableLiveData(DashboardState())
    val state: LiveData<DashboardState> = _state

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(loading = true, error = null)
            val result = repo.fetchEntities()
            _state.value = result.fold(
                onSuccess = { (list, total) -> DashboardState(items = list, total = total) },
                onFailure = { DashboardState(error = it.message ?: "Failed to load") }
            )
        }
    }
}
