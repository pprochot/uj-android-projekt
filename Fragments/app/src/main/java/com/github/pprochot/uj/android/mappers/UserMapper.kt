package com.github.pprochot.uj.android.mappers

import com.github.pprochot.uj.android.domain.response.UserResponse
import com.github.pprochot.uj.android.realmmodels.User

class UserMapper : RealmModelMapper<UserResponse, User> {

    override fun mapList(users: List<UserResponse>): List<User> {
        return users.map { map(it) }.toList()
    }

    override fun map(userResponse: UserResponse): User {
        val user = User()
        user.id = userResponse.id.toLong() //TODO change to long
        user.name = userResponse.name
        user.password = userResponse.hashedPassword
        user.isOauthUser = userResponse.isOauthUser
        return user
    }
}