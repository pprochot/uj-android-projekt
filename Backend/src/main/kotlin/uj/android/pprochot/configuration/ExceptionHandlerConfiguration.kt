package uj.android.pprochot.configuration

import com.fasterxml.jackson.databind.JsonMappingException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.postgresql.util.PSQLException
import uj.android.pprochot.exceptions.CartAlreadyExistsException
import uj.android.pprochot.exceptions.ProductListException
import uj.android.pprochot.exceptions.ResourceNotFoundException
import uj.android.pprochot.exceptions.UserAlreadyExistsException
import uj.android.pprochot.models.dto.ErrorDto

fun Application.configureExceptionHandler() {
    install(StatusPages) {
        jsonMappingException()
        resourceNotFoundException()
        userAlreadyExistsException()
        dbException()
        productListException()
        cartAlreadyExistsException()
    }
}

private fun StatusPages.Configuration.jsonMappingException() = exception<JsonMappingException> {
    call.respond(HttpStatusCode.BadRequest, ErrorDto("INVALID BODY", it.message))
}

private fun StatusPages.Configuration.resourceNotFoundException() = exception<ResourceNotFoundException> {
    call.respond(HttpStatusCode.NotFound, ErrorDto("MISSING ENTITY", it.message))
}

private fun StatusPages.Configuration.userAlreadyExistsException() = exception<UserAlreadyExistsException> {
    call.respond(HttpStatusCode.NotFound, ErrorDto("USER ALREADY EXISTS", it.message))
}

private fun StatusPages.Configuration.dbException() = exception<ExposedSQLException> {
    call.respond(HttpStatusCode.InternalServerError, ErrorDto("DB ERROR", it.message))
}

private fun StatusPages.Configuration.productListException() = exception<ProductListException> {
    call.respond(HttpStatusCode.NotFound, ErrorDto("MissingEntity", it.message))
}

private fun StatusPages.Configuration.cartAlreadyExistsException() = exception<CartAlreadyExistsException> {
    call.respond(HttpStatusCode.Conflict, ErrorDto("CART ALREADY EXISTS", it.message))
}
