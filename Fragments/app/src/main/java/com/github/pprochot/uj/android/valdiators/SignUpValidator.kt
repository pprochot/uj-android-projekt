package com.github.pprochot.uj.android.valdiators

class SignUpValidator {

    fun validate(nickname: String, password: String, secondPassword: String): Boolean {
        if (nickname.isBlank() || password.isBlank() || secondPassword.isBlank()) {
            return false
        } else if (password != secondPassword) {
            return false
        }
        return true
    }
}