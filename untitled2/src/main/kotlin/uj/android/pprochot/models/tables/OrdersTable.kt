package uj.android.pprochot.models.tables

import org.jetbrains.exposed.dao.IntIdTable

object OrdersTable : IntIdTable("orders") {
    val date = date("date")
    val customerId = reference("customerId", UsersTable)
    val cost = decimal("priceWhenBought", 12,5)
}