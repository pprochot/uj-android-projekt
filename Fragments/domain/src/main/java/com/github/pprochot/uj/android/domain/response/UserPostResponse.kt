package com.github.pprochot.uj.android.domain.response

data class UserPostResponse(
    val id: Int,
    val name: String,
    val hashedPassword: String,
    val isOauthUser: Boolean,
    val cartId: Int
)
