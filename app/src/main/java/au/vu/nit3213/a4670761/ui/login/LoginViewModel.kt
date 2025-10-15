package au.vu.nit3213.a4670761.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.vu.nit3213.a4670761.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

data class LoginState(
    val loading: Boolean = false,
    val error: String? = null,
    val navigateDashboard: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _state = MutableLiveData(LoginState())
    val state: LiveData<LoginState> = _state

    fun login(username: String, password: String, campus: String) {
        if (username.isBlank() || password.isBlank()) {
            _state.value = _state.value?.copy(error = "Please enter username and student ID")
            return
        }

        viewModelScope.launch {
            _state.value = LoginState(loading = true)
            val result = authRepo.loginAndSave(username, password, campus)
            _state.value = when {
                result.isSuccess -> LoginState(navigateDashboard = true)
                else -> LoginState(error = result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun onNavigated() { _state.value = _state.value?.copy(navigateDashboard = false) }
}
