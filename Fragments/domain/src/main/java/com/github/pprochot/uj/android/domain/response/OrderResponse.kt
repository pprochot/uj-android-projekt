package com.github.pprochot.uj.android.domain.response

import org.joda.time.DateTime
import java.math.BigDecimal

data class OrderResponse(
    val id: Int,
    val user: UserResponse,
    val products: List<ProductResponse>,
    val cost: BigDecimal,
    val date: DateTime
)