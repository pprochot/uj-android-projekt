package com.github.pprochot.uj.android.fragments.models

import io.realm.RealmObject
import io.realm.annotations.Ignore
import java.math.BigDecimal

open class Money : RealmObject() {

    var dbValue: String = ""
    var value: BigDecimal
        get() = BigDecimal(dbValue)
        set(moneyValue) {
            dbValue = moneyValue.toString()
        }
}