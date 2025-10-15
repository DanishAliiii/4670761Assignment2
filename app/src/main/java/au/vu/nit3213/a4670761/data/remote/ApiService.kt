package au.vu.nit3213.a4670761.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// ---- Request/Response models ----
data class AuthRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val keypass: String
)

data class EntityDto(
    val property1: String?,
    val property2: String?,
    val description: String?
)

data class DashboardResponse(
    val entities: List<EntityDto>,
    val entityTotal: Int
)

// ---- Retrofit service ----
// NOTE: both endpoints return their bodies directly (not Response<...>)
interface ApiService {

    @POST("{campus}/auth")
    suspend fun login(
        @Path("campus") campus: String,
        @Body body: AuthRequest
    ): AuthResponse

    @GET("dashboard/{keypass}")
    suspend fun dashboard(
        @Path("keypass") keypass: String
    ): DashboardResponse
}
