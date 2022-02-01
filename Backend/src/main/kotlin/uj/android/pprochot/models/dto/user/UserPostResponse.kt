package uj.android.pprochot.models.dto.user

data class UserPostResponse(
    val id: Int,
    val name: String,
    val hashedPassword: String,
    val isOauthUser: Boolean,
    val cartId: Int
)
