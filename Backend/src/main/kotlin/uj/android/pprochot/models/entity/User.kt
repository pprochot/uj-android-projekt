package uj.android.pprochot.models.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import uj.android.pprochot.models.tables.UsersTable

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UsersTable)

    var name by UsersTable.name
    var password by UsersTable.password
    var isOauthUser by UsersTable.isOauthUser
}
