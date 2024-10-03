package sp.bvantur.tasky.login.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginUserDataResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("fullName")
    val name: String,
    @SerialName("userId")
    val userId: String,
    @SerialName("accessTokenExpirationTimestamp")
    val accessTokenExpirationTimestamp: Long
)
