package uj.android.pprochot.service

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import uj.android.pprochot.exceptions.ProductListException
import uj.android.pprochot.exceptions.ResourceNotFoundException
import uj.android.pprochot.mappers.OrderMapper
import uj.android.pprochot.models.dto.ListResponse
import uj.android.pprochot.models.dto.order.OrderRequest
import uj.android.pprochot.models.dto.order.OrderResponse
import uj.android.pprochot.models.entity.Order
import uj.android.pprochot.models.entity.Product
import uj.android.pprochot.models.entity.User
import uj.android.pprochot.models.tables.OrdersProductTable
import uj.android.pprochot.models.tables.OrdersTable
import uj.android.pprochot.models.tables.ProductsTable
import java.math.BigDecimal

class OrderService(private val orderMapper: OrderMapper) : CrudService<OrderRequest, OrderResponse> {

    override fun create(request: OrderRequest): OrderResponse = transaction {
        if (request.products.isEmpty())
            throw ProductListException("Need at least one product!")
        val user = User.findById(request.customerId)
            ?: throw ResourceNotFoundException("User with id ${request.customerId} not found")

        val productsToAdd = request.products.mapNotNull {
            Product.findById(it)
        }

        if (productsToAdd.count() != request.products.size)
            throw ProductListException("List contains not existing products!")

        val createdOrderId = OrdersTable.insertAndGetId {
            it[customerId] = user.id
            it[date] = DateTime.now()
            it[cost] = productsToAdd.map(Product::cost).reduce(BigDecimal::add)
        }

        productsToAdd.forEach { product ->
            OrdersProductTable.insert {
                it[orderId] = createdOrderId
                it[productId] = product.id
            }
        }

        val order = Order.findById(createdOrderId)
        return@transaction orderMapper.toResponse(order!!)
    }

    override fun getAll(): ListResponse<OrderResponse> = transaction {
        return@transaction ListResponse(Order.all().map(orderMapper::toResponse))
    }

    override fun getById(id: Int): OrderResponse = transaction {
        val order = Order.findById(id)
            ?: throw ResourceNotFoundException("Order with id $id not found")
        return@transaction orderMapper.toResponse(order)
    }

    override fun update(id: Int, request: OrderRequest): OrderResponse = transaction {
        if (request.products.isEmpty())
            throw ProductListException("Need at least one product!")
        val order = Order.findById(id)
            ?: throw ResourceNotFoundException("Order with id $id not found")
        val user = User.findById(request.customerId)
            ?: throw ResourceNotFoundException("User with id ${request.customerId} not found")
        val products = Product.find { ProductsTable.id inList request.products }
        if (products.count() != request.products.size)
            throw ProductListException("List contains not existing products!")
        order.apply {
            customer = user
            cost = products.map(Product::cost).reduce(BigDecimal::add)
            date = DateTime.now()
        }
        order.products = products
        return@transaction orderMapper.toResponse(order)
    }

    override fun delete(id: Int) = transaction {
        val order = Order.findById(id)
            ?: throw ResourceNotFoundException("Order with id $id not found")
        order.delete()
    }
}