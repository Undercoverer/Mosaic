package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationAccount(
    val username: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisteredAccount(
    val accountId: Int,
    val token: String
)

@Serializable
data class LoginAccount(
    val email: String,
    val password: String
)

@Serializable
data class AccountResponse(
    val accountID: Int,
    val username: String,
    val email: String,
    val password: String,
    val token: String
)

@Serializable
data class AccountDisplayResponse(
    val id: Int,
    val username: String
)
