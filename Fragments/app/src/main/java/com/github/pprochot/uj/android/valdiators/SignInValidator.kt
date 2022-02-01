package com.github.pprochot.uj.android.valdiators

class SignInValidator {

    fun validate(nickname: String, password: String): Boolean {
        if (nickname.isBlank() || password.isBlank()) {
            return false
        }
        return true
    }
}