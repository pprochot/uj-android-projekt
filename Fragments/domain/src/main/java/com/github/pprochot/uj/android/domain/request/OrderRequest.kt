package com.github.pprochot.uj.android.domain.request

data class OrderRequest(val customerId: Int, val products: List<Int>)