package com.github.pprochot.uj.android.domain.response

import java.math.BigDecimal

data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val cost: BigDecimal,
    val category: CategoryResponse
)