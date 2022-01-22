package com.github.pprochot.uj.android.services

import com.github.pprochot.uj.android.domain.ListResponse
import com.github.pprochot.uj.android.domain.request.CartRequest
import com.github.pprochot.uj.android.domain.response.CartResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartService {

    companion object {
        private const val CARTS_ENDPOINT = "/carts"
        private const val CARTS_ENDPOINT_WITH_ID = "/carts/{id}"
    }

    @POST(CARTS_ENDPOINT)
    fun create(@Body request: CartRequest): Call<CartResponse>

    @GET(CARTS_ENDPOINT)
    fun getAll(): Call<ListResponse<CartResponse>>

    @GET(CARTS_ENDPOINT_WITH_ID)
    fun getById(@Path("id") id: Int): Call<CartResponse>

    @PUT(CARTS_ENDPOINT_WITH_ID)
    fun update(@Path("id") id: Int, @Body request: CartRequest): Call<CartResponse>

    @DELETE(CARTS_ENDPOINT_WITH_ID)
    fun delete(@Path("id") id: Int)
}