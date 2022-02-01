package uj.android.pprochot.models.dto.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(val customerId: Int, val products: List<Int>)