package com.github.pprochot.uj.android.mappers

import com.github.pprochot.uj.android.domain.response.OrderResponse
import com.github.pprochot.uj.android.domain.response.ProductResponse
import com.github.pprochot.uj.android.realmmodels.Money
import com.github.pprochot.uj.android.realmmodels.Order
import com.github.pprochot.uj.android.realmmodels.Product
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val userMapper: UserMapper,
    private val productMapper: ProductMapper
) :
    RealmModelMapper<OrderResponse, Order> {

    override fun mapList(orders: List<OrderResponse>): List<Order> {
        return orders.map { map(it) }.toList()
    }

    override fun map(orderResponse: OrderResponse): Order {
        val order = Order()
        order.id = orderResponse.id
        order.date = orderResponse.date.toDate()
        order.customer = userMapper.map(orderResponse.user)
        order.cost = Money(orderResponse.cost)
        productMapper.mapList(orderResponse.products).forEach {
            order.products.add(it)
        }
        return order
    }
}