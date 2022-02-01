package uj.android.pprochot

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import uj.android.pprochot.api.createPaymentIntentRoute
import uj.android.pprochot.api.genericCrudRoute
import uj.android.pprochot.api.userRoute
import uj.android.pprochot.configuration.DatabaseConfig
import uj.android.pprochot.configuration.configureExceptionHandler
import uj.android.pprochot.mappers.*
import uj.android.pprochot.service.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val databaseConfig = DatabaseConfig(environment.config)
    databaseConfig.createTables()

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
    val cartService = CartService(cartMapper)

    routing {
        userRoute("/users", UserService(userMapper, cartService))
        genericCrudRoute("/products", ProductService(productMapper))
        genericCrudRoute("/categories", CategoryService(categoryMapper))
        genericCrudRoute("/carts", CartService(cartMapper))
        genericCrudRoute("/orders", OrderService(orderMapper))
        createPaymentIntentRoute()
    }
}