package uj.android.pprochot.models.tables

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object CartsProductTable : IntIdTable("CartsProductTable") {
    val cartsId = reference("cartsId", CartsTable, onDelete = ReferenceOption.CASCADE).primaryKey()
    val productId = reference("productId", ProductsTable).primaryKey()
}
