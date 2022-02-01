package uj.android.pprochot.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import uj.android.pprochot.models.entity.Cart
import uj.android.pprochot.models.tables.CartsTable
import java.math.BigDecimal

fun Route.createPaymentIntentRoute() {
    get("/create-payment-intent/{customer_id}") {
        val customerId = call.parameters["customer_id"]
        Stripe.apiKey =
            "sk_test_51KN37zHI4qTI0HVaBmsxwne4XXGaEY9IqrGe6msHuqxjQdPF9uFqGcGP2mei1rF6DGXVlE0UnOJFe9w4aXngih6400ZO9qojZM"

        var cost: Long? = null
        transaction {
            val cart = Cart.find { CartsTable.ownerId eq customerId?.toInt() }.first()
            if (!cart.products.empty())
                cost = cart.products.map { it.cost }.reduce(BigDecimal::add).toLong()
            else
                cost = 0
        }
        val params = PaymentIntentCreateParams.builder()
            .setAmount(cost!! * 100)
            .setCurrency("pln")
            .build()

        val intent = PaymentIntent.create(params)
        call.respond(ObjectMapper().writeValueAsString(intent.clientSecret))
    }
}