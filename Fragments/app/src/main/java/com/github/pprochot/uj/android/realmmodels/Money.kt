package com.github.pprochot.uj.android.realmmodels

import io.realm.RealmObject
import java.math.BigDecimal

open class Money(): RealmObject() {

    constructor(value: BigDecimal) : this() {
        this.value = value
    }

    var dbValue: String = ""
    var value: BigDecimal
        get() = BigDecimal(dbValue)
        set(moneyValue) {
            dbValue = moneyValue.toString()
        }
}