package com.github.pprochot.uj.android.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var password: String = ""

    fun verifyPassword(password: String): Boolean {
        return this.password == password;
    }
}