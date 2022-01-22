package uj.android.pprochot.models.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import uj.android.pprochot.models.tables.ProductsTable

class Product(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<Product>(ProductsTable)

    var name by ProductsTable.name
    var description by ProductsTable.description
    var cost by ProductsTable.cost
    var category by Category referencedOn ProductsTable.category
}