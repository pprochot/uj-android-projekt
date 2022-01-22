package uj.android.pprochot.models.tables

import org.jetbrains.exposed.dao.IntIdTable

object CategoriesTable : IntIdTable("categories") {
    val name = varchar("name", 255)
    val description = varchar("description", 255)
}