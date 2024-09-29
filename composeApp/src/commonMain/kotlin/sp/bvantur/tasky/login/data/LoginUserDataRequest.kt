package sp.bvantur.tasky.login.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginUserDataRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)
