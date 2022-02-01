package uj.android.pprochot.models.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val id: Int, val name: String, val hashedPassword: String, val isOauthUser: Boolean)
