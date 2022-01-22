package uj.android.pprochot

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.joda.JodaModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import uj.android.pprochot.api.*
import uj.android.pprochot.configuration.DatabaseConfig
import uj.android.pprochot.configuration.configureExceptionHandler
import uj.android.pprochot.mappers.*
import uj.android.pprochot.service.*

fun main() {
    val databaseConfig = DatabaseConfig()
    databaseConfig.createTables()

    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
                registerModule(JodaModule())
            }
        }
        configureExceptionHandler()

        val categoryMapper = CategoryMapper()
        val userMapper = UserMapper()
        val productMapper = ProductMapper()
        val cartMapper = CartMapper(userMapper, productMapper)
        val orderMapper = OrderMapper(userMapper, productMapper)

        routing {
            genericCrudRoute("/users", UserService(userMapper))
            genericCrudRoute("/products", ProductService(productMapper))
            genericCrudRoute("/categories", CategoryService(categoryMapper))
            genericCrudRoute("/carts", CartService(cartMapper))
            genericCrudRoute("/orders", OrderService(orderMapper))
        }
    }.start(wait = true)
}