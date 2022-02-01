package uj.android.pprochot.models.dto.product

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import uj.android.pprochot.models.dto.catalog.CategoryResponse
import java.math.BigDecimal

@Serializable
data class ProductResponse(val id: Int, val name: String, val description: String, @Contextual val cost: BigDecimal, val category: CategoryResponse)