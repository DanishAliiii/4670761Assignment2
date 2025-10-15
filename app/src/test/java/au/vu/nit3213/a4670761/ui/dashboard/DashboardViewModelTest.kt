package au.vu.nit3213.a4670761.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.vu.nit3213.a4670761.data.repo.DashboardRepository
import au.vu.nit3213.a4670761.domain.model.Entity
import com.google.common.truth.Truth.assertThat
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
class DashboardViewModelTest {

    @get:Rule
    val instant = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repo: DashboardRepository
    private lateinit var vm: DashboardViewModel

    // --- simple LiveData helper ---
    private fun <T> androidx.lifecycle.LiveData<T>.getOrAwait(): T {
        var value: T? = null
        val latch = java.util.concurrent.CountDownLatch(1)
        val obs = object : androidx.lifecycle.Observer<T> {
            override fun onChanged(t: T) {
                value = t
                latch.countDown()
                this@getOrAwait.removeObserver(this)
            }
        }
        this.observeForever(obs)
        latch.await()
        return value as T
    }

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repo = mock()
        vm = DashboardViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load sets items when repository succeeds`() = runTest(dispatcher) {
        // Given
        val items = listOf(Entity("Title 1", "Sub 1", "Desc 1"))
        whenever(repo.fetchEntities()).thenReturn(Result.success(items to items.size))

        // When
        vm.load()
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = vm.state.getOrAwait()
        assertThat(state.loading).isFalse()
        assertThat(state.items).hasSize(1)
        assertThat(state.items[0].property1).isEqualTo("Title 1")
        assertThat(state.error).isNull()
        assertThat(state.total).isEqualTo(1)
    }

    @Test
    fun `load sets error when repository fails`() = runTest(dispatcher) {
        whenever(repo.fetchEntities()).thenReturn(Result.failure(IllegalStateException("boom")))

        vm.load()
        dispatcher.scheduler.advanceUntilIdle()

        val state = vm.state.getOrAwait()
        assertThat(state.loading).isFalse()
        assertThat(state.items).isEmpty()
        assertThat(state.error).contains("boom")
    }
}
