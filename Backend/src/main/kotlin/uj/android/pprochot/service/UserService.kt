package uj.android.pprochot.service

import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import uj.android.pprochot.exceptions.ResourceNotFoundException
import uj.android.pprochot.exceptions.UserAlreadyExistsException
import uj.android.pprochot.mappers.UserMapper
import uj.android.pprochot.models.dto.ListResponse
import uj.android.pprochot.models.dto.cart.CartRequest
import uj.android.pprochot.models.dto.user.UserPostResponse
import uj.android.pprochot.models.dto.user.UserRequest
import uj.android.pprochot.models.dto.user.UserResponse
import uj.android.pprochot.models.entity.User
import uj.android.pprochot.models.tables.UsersTable

class UserService(private val userMapper: UserMapper, private val cartService: CartService) {

    fun create(request: UserRequest): UserPostResponse = transaction {
        val isEmpty = User.find { UsersTable.name eq request.name }.empty()
        if (!isEmpty)
            throw UserAlreadyExistsException(request.name)
        val user = when (request.isOauthUser) {
            true -> User.new {
                    name = request.name
                    password = ""
                    isOauthUser = true
                }
            false -> User.new {
                name = request.name
                password = BCrypt.hashpw(request.password, BCrypt.gensalt())
                isOauthUser = false
            }
        }
        val cart = cartService.create(CartRequest(user.id.value, emptyList()))
        return@transaction userMapper.toPostResponse(user, cart.id)
    }

    fun getById(id: Int) = transaction {
        val category = User.findById(id)
            ?: throw ResourceNotFoundException("User with id $id not found")
        return@transaction userMapper.toResponse(category)
    }

    fun getAll(): ListResponse<UserResponse> = transaction {
        val users = User.all()
            .map(userMapper::toResponse)
            .toList()
        return@transaction ListResponse(users)
    }

    fun update(id: Int, request: UserRequest): UserResponse = transaction {
        val user = User.findById(id)
            ?: throw ResourceNotFoundException("User with id $id not found")
       user.apply {
            name = request.name
            password = BCrypt.hashpw(request.password, BCrypt.gensalt())
        }
        return@transaction userMapper.toResponse(user)
    }

    fun delete(id: Int) = transaction {
        val user = User.findById(id)
            ?: throw ResourceNotFoundException("User with id $id not found")
        user.delete()
    }
}