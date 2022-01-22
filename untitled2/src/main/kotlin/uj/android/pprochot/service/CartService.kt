package uj.android.pprochot.service

import org.jetbrains.exposed.sql.transactions.transaction
import uj.android.pprochot.exceptions.ProductListException
import uj.android.pprochot.exceptions.ResourceNotFoundException
import uj.android.pprochot.mappers.CartMapper
import uj.android.pprochot.models.dto.ListResponse
import uj.android.pprochot.models.dto.cart.CartRequest
import uj.android.pprochot.models.dto.cart.CartResponse
import uj.android.pprochot.models.entity.Cart
import uj.android.pprochot.models.entity.Category
import uj.android.pprochot.models.entity.Product
import uj.android.pprochot.models.entity.User
import uj.android.pprochot.models.tables.ProductsTable

class CartService(private val cartMapper: CartMapper) : CrudService<CartRequest, CartResponse> {

    override fun create(request: CartRequest): CartResponse = transaction {
        val user = User.findById(request.ownerId)
            ?: throw ResourceNotFoundException("User with id ${request.ownerId} not found")
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
        val user = User.findById(request.ownerId)
            ?: throw ResourceNotFoundException("User with id ${request.ownerId} not found")
        val products = Product.find { ProductsTable.id inList request.products }
        if (products.count() != request.products.size)
            throw ProductListException("List contains not existing products!")
        cart.apply {
            owner = user
        }
        cart.products = products
        return@transaction cartMapper.toResponse(cart)
    }

    override fun delete(id: Int) = transaction {
        val cart = Cart.findById(id)
            ?: throw ResourceNotFoundException("Cart with id $id not found")
        cart.delete()
    }
}