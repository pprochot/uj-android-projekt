package uj.android.pprochot.models.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import uj.android.pprochot.models.tables.CartsProductTable
import uj.android.pprochot.models.tables.CartsTable

class Cart(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Cart>(CartsTable)

    var products by Product via CartsProductTable
    var owner by User referencedOn CartsTable.ownerId
}