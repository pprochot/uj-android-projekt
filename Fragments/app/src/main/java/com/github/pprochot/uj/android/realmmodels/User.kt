package com.github.pprochot.uj.android.realmmodels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var password: String = ""
    var isOauthUser: Boolean = false
}