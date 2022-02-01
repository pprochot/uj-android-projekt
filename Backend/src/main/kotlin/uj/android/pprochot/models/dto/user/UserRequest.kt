package uj.android.pprochot.models.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(val name: String, val password: String, val isOauthUser: Boolean)
