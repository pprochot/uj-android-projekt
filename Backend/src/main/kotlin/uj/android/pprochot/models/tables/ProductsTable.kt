package uj.android.pprochot.models.tables

import org.jetbrains.exposed.dao.IntIdTable

object ProductsTable : IntIdTable("products") {
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val category = reference("categoryId", CategoriesTable)
    val cost = decimal("cost", 12, 5)
}