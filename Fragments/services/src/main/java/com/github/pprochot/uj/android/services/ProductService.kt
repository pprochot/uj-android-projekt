package com.github.pprochot.uj.android.services

import com.github.pprochot.uj.android.domain.ListResponse
import com.github.pprochot.uj.android.domain.request.ProductRequest
import com.github.pprochot.uj.android.domain.response.ProductResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {

    companion object {
        private const val PRODUCTS_ENDPOINT = "/products"
        private const val PRODUCTS_ENDPOINT_WITH_ID = "/products/{id}"
    }

    @POST(PRODUCTS_ENDPOINT)
    fun create(@Body request: ProductRequest): Call<ProductResponse>

    @GET(PRODUCTS_ENDPOINT)
    fun getAll(): Call<ListResponse<ProductResponse>>

    @GET(PRODUCTS_ENDPOINT_WITH_ID)
    fun getById(@Path("id") id: Int): Call<ProductResponse>

    @PUT(PRODUCTS_ENDPOINT_WITH_ID)
    fun update(@Path("id") id: Int, @Body request: ProductRequest): Call<ProductResponse>

    @DELETE(PRODUCTS_ENDPOINT_WITH_ID)
    fun delete(@Path("id") id: Int)
}