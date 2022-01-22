package uj.android.pprochot.mappers

import uj.android.pprochot.models.dto.cart.CartResponse
import uj.android.pprochot.models.entity.Cart

class CartMapper(private val userMapper: UserMapper, private val productMapper: ProductMapper) {

    fun toResponse(cart: Cart): CartResponse {
        val productResponses = cart.products.map(productMapper::toResponse).toList()
        return CartResponse(cart.id.value, userMapper.toResponse(cart.owner), productResponses)
    }
}