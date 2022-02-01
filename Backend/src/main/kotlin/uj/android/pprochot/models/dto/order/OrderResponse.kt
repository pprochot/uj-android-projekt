package uj.android.pprochot.models.dto.order

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.joda.time.DateTime
import uj.android.pprochot.models.dto.product.ProductResponse
import uj.android.pprochot.models.dto.user.UserResponse
import java.math.BigDecimal

@Serializable
data class OrderResponse(
    val id: Int,
    val user: UserResponse,
    val products: List<ProductResponse>,
    @Contextual val cost: BigDecimal,
    @Contextual val date: DateTime
)