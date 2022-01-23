package com.github.pprochot.uj.android.mappers

import com.github.pprochot.uj.android.domain.response.CartResponse
import com.github.pprochot.uj.android.realmmodels.Cart
import javax.inject.Inject

class CartMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val productMapper: ProductMapper
) : RealmModelMapper<CartResponse, Cart> {

    override fun mapList(carts: List<CartResponse>): List<Cart> {
        return carts.map { map(it) }.toList()
    }

    override fun map(cartResponse: CartResponse): Cart {
        val cart = Cart()
        cart.id = cartResponse.id
        cart.owner = userMapper.map(cartResponse.user)
        productMapper.mapList(cartResponse.products).forEach {
            cart.products.add(it)
        }
        return cart
    }
}