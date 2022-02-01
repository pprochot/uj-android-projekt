package uj.android.pprochot.service

import io.ktor.html.*
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import uj.android.pprochot.exceptions.CartAlreadyExistsException
import uj.android.pprochot.exceptions.ProductListException
import uj.android.pprochot.exceptions.ResourceNotFoundException
import uj.android.pprochot.mappers.CartMapper
import uj.android.pprochot.models.dto.ListResponse
import uj.android.pprochot.models.dto.cart.CartRequest
import uj.android.pprochot.models.dto.cart.CartResponse
import uj.android.pprochot.models.entity.Cart
import uj.android.pprochot.models.entity.Product
import uj.android.pprochot.models.entity.User
import uj.android.pprochot.models.tables.CartsProductTable
import uj.android.pprochot.models.tables.CartsTable
import uj.android.pprochot.models.tables.ProductsTable

class CartService(private val cartMapper: CartMapper) : CrudService<CartRequest, CartResponse> {

    override fun create(request: CartRequest): CartResponse = transaction {
        val user = User.findById(request.ownerId)
            ?: throw ResourceNotFoundException("User with id ${request.ownerId} not found")

        val userCarts = Cart.find { CartsTable.ownerId eq user.id.value }
        if (!userCarts.empty())
            throw CartAlreadyExistsException("Cart already exists for this user!")

        val products = Product.find { ProductsTable.id inList request.products }
        if (products.count() != request.products.size)
            throw ProductListException("List contains not existing products!")
        val cart = Cart.new {
            owner = user
        }
        cart.products = products
        return@transaction cartMapper.toResponse(cart)
    }

    override fun getAll(): ListResponse<CartResponse> = transaction {
        return@transaction ListResponse(Cart.all().map(cartMapper::toResponse))
    }

    override fun getById(id: Int): CartResponse = transaction {
        val cart = Cart.findById(id)
            ?: throw ResourceNotFoundException("Catalog with id $id not found")
        return@transaction cartMapper.toResponse(cart)
    }

    override fun update(id: Int, request: CartRequest): CartResponse = transaction {
        val cart = Cart.findById(id)
            ?: throw ResourceNotFoundException("Cart with id $id not found")

        val productsToAdd = request.products.mapNotNull {
            Product.findById(it)
        }

        if (productsToAdd.count() != request.products.size)
            throw ProductListException("List contains not existing products!")

        CartsProductTable.deleteWhere {
            CartsProductTable.cartsId eq cart.id
        }
        productsToAdd.forEach { product ->
            CartsProductTable.insert {
                it[cartsId] = cart.id
                it[productId] = product.id
            }
        }

        return@transaction cartMapper.toResponse(cart)
    }

    override fun delete(id: Int) = transaction {
        val cart = Cart.findById(id)
            ?: throw ResourceNotFoundException("Cart with id $id not found")
        cart.delete()
    }
}