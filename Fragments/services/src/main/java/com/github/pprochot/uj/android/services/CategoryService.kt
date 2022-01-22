package com.github.pprochot.uj.android.services

import com.github.pprochot.uj.android.domain.ListResponse
import com.github.pprochot.uj.android.domain.request.CategoryRequest
import com.github.pprochot.uj.android.domain.response.CategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryService {

    companion object {
        private const val CATEGORIES_ENDPOINT = "/categories"
        private const val CATEGORIES_ENDPOINT_WITH_ID = "/categories/{id}"
    }

    @POST(CATEGORIES_ENDPOINT)
    fun create(@Body request: CategoryRequest): Call<CategoryResponse>

    @GET(CATEGORIES_ENDPOINT)
    fun getAll(): Call<ListResponse<CategoryResponse>>

    @GET(CATEGORIES_ENDPOINT_WITH_ID)
    fun getById(@Path("id") id: Int): Call<CategoryResponse>

    @PUT(CATEGORIES_ENDPOINT_WITH_ID)
    fun update(@Path("id") id: Int, @Body request: CategoryRequest): Call<CategoryResponse>

    @DELETE(CATEGORIES_ENDPOINT_WITH_ID)
    fun delete(@Path("id") id: Int)
}