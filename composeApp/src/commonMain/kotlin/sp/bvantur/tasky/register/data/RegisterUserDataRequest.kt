package sp.bvantur.tasky.register.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserDataRequest(
    @SerialName("fullName")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)
