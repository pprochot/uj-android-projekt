package com.github.pprochot.uj.android.realmmodels

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Cart : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var products: RealmList<Product> = RealmList()
    var owner: User? = null
}