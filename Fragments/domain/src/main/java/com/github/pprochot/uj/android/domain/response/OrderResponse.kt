package com.github.pprochot.uj.android.domain.response

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val id: Int,
    val user: UserResponse,
    val products: List<ProductResponse>,
    val cost: BigDecimal,
    val date: LocalDateTime
)