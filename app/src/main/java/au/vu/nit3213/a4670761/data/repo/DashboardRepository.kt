package au.vu.nit3213.a4670761.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import au.vu.nit3213.a4670761.data.remote.ApiService
import au.vu.nit3213.a4670761.domain.model.Entity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val api: ApiService,
    private val prefs: DataStore<Preferences>
) {

    private val KEY_KEYPASS = stringPreferencesKey("keypass")

    suspend fun fetchEntities(): Result<Pair<List<Entity>, Int>> = runCatching {
        // Get saved keypass from DataStore
        val keypass = prefs.data.first()[KEY_KEYPASS]
            ?: throw IllegalStateException("Missing keypass. Please login again.")

        // Call the dashboard API
        val resp = api.dashboard(keypass)

        // Map the response to your Entity model
        val list = resp.entities.map { dto ->
            Entity(
                property1 = dto.property1 ?: "",
                property2 = dto.property2 ?: "",
                description = dto.description ?: ""
            )
        }

        // Return the mapped list with the total count
        list to resp.entityTotal
    }
}
