package au.vu.nit3213.a4670761.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.vu.nit3213.a4670761.data.repo.AuthRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    // Executes LiveData tasks synchronously
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var vm: LoginViewModel
    private val repo: AuthRepository = mock()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = LoginViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success navigates to dashboard and clears error`() = runTest(dispatcher) {
        // Given
        whenever(repo.loginAndSave("danish", "12345678", "footscray"))
            .thenReturn(Result.success(Unit))

        // When
        vm.login("danish", "12345678", "footscray")
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        val s = vm.state.value!!
        Truth.assertThat(s.loading).isFalse()
        Truth.assertThat(s.error).isNull()
        Truth.assertThat(s.navigateDashboard).isTrue()
    }

    @Test
    fun `login failure sets error and does not navigate`() = runTest(dispatcher) {
        // Given
        whenever(repo.loginAndSave("danish", "bad", "footscray"))
            .thenReturn(Result.failure(Exception("Invalid")))

        // When
        vm.login("danish", "bad", "footscray")
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        val s = vm.state.value!!
        Truth.assertThat(s.loading).isFalse()
        Truth.assertThat(s.error).isNotEmpty()
        Truth.assertThat(s.navigateDashboard).isFalse()
    }
}