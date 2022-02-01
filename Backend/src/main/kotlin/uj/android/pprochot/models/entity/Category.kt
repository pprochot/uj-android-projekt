package uj.android.pprochot.models.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import uj.android.pprochot.models.tables.CategoriesTable

class Category(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Category>(CategoriesTable)

    var name by CategoriesTable.name
    var description by CategoriesTable.description
}