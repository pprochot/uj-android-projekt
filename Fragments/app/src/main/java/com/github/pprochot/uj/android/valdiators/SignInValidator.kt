package com.github.pprochot.uj.android.valdiators

import com.github.pprochot.uj.android.services.UserService
import javax.inject.Inject

class SignInValidator {

    fun validate(nickname: String, password: String): Boolean {
        if (nickname.isBlank() || password.isBlank()) {
            return false
        }
        return true
    }
}