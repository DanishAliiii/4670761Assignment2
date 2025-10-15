package au.vu.nit3213.a4670761.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import au.vu.nit3213.a4670761.data.remote.ApiService
import au.vu.nit3213.a4670761.data.remote.AuthRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val prefs: DataStore<Preferences>
) {
    companion object {
        private val KEY_KEYPASS = stringPreferencesKey("keypass")
        private val KEY_CAMPUS = stringPreferencesKey("campus")
    }

    suspend fun loginAndSave(username: String, password: String, campus: String): Result<Unit> =
        runCatching {
            // Call API and get body directly
            val resp = api.login(campus = campus, body = AuthRequest(username, password))

            // Persist keypass + campus
            prefs.edit {
                it[KEY_KEYPASS] = resp.keypass
                it[KEY_CAMPUS] = campus
            }
        }
}
