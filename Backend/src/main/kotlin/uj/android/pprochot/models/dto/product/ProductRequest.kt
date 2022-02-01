package uj.android.pprochot.models.dto.product

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class ProductRequest(
    val name: String,
    val description: String,
    val cost: @Contextual BigDecimal,
    val categoryId: Int
)
