package com.github.pprochot.uj.android.domain.request

import java.math.BigDecimal

data class ProductRequest(
    val name: String,
    val description: String,
    val cost: BigDecimal,
    val categoryId: Int
)
