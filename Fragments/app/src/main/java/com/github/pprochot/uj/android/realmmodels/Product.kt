package com.github.pprochot.uj.android.realmmodels

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.math.BigDecimal

open class Product : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var description: String = ""
    var cost: Money? = null
    var category: Category? = null

    fun costValue(): BigDecimal? {
        return cost?.value;
    }
}