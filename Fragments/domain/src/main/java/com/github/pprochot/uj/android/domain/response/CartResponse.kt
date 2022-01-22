package com.github.pprochot.uj.android.domain.response

data class CartResponse(val id: Int, val user: UserResponse, val products: List<ProductResponse>)