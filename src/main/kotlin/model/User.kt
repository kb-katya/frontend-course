package model

import kotlinx.serialization.Serializable

@Serializable
class User (
    val id: Int,
    val name: String,
    val email: String,
    var password: String,
    val active: Boolean = true
)

@Serializable
data class LoginUserPayload(val email: String, val password: String)

@Serializable
data class SignUpUserPayload(val name: String, val email: String, val password: String)

@Serializable
data class UserWithToken(val token: String, val user: User)

