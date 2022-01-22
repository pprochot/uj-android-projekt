package com.github.pprochot.uj.android.services

import com.github.pprochot.uj.android.domain.ListResponse
import com.github.pprochot.uj.android.domain.request.UserRequest
import com.github.pprochot.uj.android.domain.response.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    companion object {
        private const val USERS_ENDPOINT = "/users"
        private const val USERS_ENDPOINT_WITH_ID = "/users/{id}"
    }

    @POST(USERS_ENDPOINT)
    fun create(@Body request: UserRequest): Call<UserResponse>

    @GET(USERS_ENDPOINT)
    fun getAll(): Call<ListResponse<UserResponse>>

    @GET(USERS_ENDPOINT_WITH_ID)
    fun getById(@Path("id") id: Int): Call<UserResponse>

    @PUT(USERS_ENDPOINT_WITH_ID)
    fun update(@Path("id") id: Int, @Body request: UserRequest): Call<UserResponse>

    @DELETE(USERS_ENDPOINT_WITH_ID)
    fun delete(@Path("id") id: Int)
}
