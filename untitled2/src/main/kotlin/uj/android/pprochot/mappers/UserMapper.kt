package uj.android.pprochot.mappers

import uj.android.pprochot.models.dto.user.UserResponse
import uj.android.pprochot.models.entity.User

class UserMapper {

    fun toResponse(user: User): UserResponse {
        return UserResponse(user.id.value, user.name, user.password)
    }
}