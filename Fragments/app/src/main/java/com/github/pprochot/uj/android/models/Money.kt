package com.github.pprochot.uj.android.models

import io.realm.RealmObject
import java.math.BigDecimal

open class Money : RealmObject() {

    var dbValue: String = ""
    var value: BigDecimal
        get() = BigDecimal(dbValue)
        set(moneyValue) {
            dbValue = moneyValue.toString()
        }
}