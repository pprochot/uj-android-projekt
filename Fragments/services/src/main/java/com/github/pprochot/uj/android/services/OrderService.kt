package com.github.pprochot.uj.android.services

import com.github.pprochot.uj.android.domain.ListResponse
import com.github.pprochot.uj.android.domain.request.OrderRequest
import com.github.pprochot.uj.android.domain.response.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderService {

    companion object {
        private const val ORDERS_ENDPOINT = "/orders"
        private const val ORDERS_ENDPOINT_WITH_ID = "/orders/{id}"
    }

    @POST(ORDERS_ENDPOINT)
    fun create(@Body request: OrderRequest): Call<OrderResponse>

    @GET(ORDERS_ENDPOINT)
    fun getAll(): Call<ListResponse<OrderResponse>>

    @GET(ORDERS_ENDPOINT_WITH_ID)
    fun getById(@Path("id") id: Int): Call<OrderResponse>

    @PUT(ORDERS_ENDPOINT_WITH_ID)
    fun update(@Path("id") id: Int, @Body request: OrderRequest): Call<OrderResponse>

    @DELETE(ORDERS_ENDPOINT_WITH_ID)
    fun delete(@Path("id") id: Int)
}