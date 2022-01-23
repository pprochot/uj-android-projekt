package com.github.pprochot.uj.android.realmmodels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Category : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var description: String = ""
}