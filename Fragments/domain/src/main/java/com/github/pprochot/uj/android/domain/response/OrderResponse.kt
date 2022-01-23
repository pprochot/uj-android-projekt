package com.github.pprochot.uj.android.domain.response

import org.joda.time.DateTime
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime

data class OrderResponse(
    val id: Int,
    val user: UserResponse,
    val products: List<ProductResponse>,
    val cost: BigDecimal,
    val date: LocalDateTime
)