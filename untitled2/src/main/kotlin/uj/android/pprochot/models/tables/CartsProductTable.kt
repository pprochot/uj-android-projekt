package uj.android.pprochot.models.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object CartsProductTable : Table("CartsProductTable") {
    val cartsId = reference("cartsId", CartsTable, onDelete = ReferenceOption.CASCADE).primaryKey()
    val productId = reference("productId", ProductsTable).primaryKey()
}
