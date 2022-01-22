package uj.android.pprochot.models.tables

import org.jetbrains.exposed.dao.IntIdTable

object CartsTable : IntIdTable("carts") {
    val ownerId = reference("ownerId", UsersTable)
}