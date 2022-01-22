package uj.android.pprochot.models.dto.cart

import uj.android.pprochot.models.dto.product.ProductResponse
import uj.android.pprochot.models.dto.user.UserResponse

data class CartResponse(val id: Int, val user: UserResponse, val products: List<ProductResponse>)