package com.github.pprochot.uj.android.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StripeService {

    companion object {
        private const val CREATE_PAYMENT_INTENT_ENDPOINT = "/create-payment-intent/{customer_id}"
    }

    @GET(CREATE_PAYMENT_INTENT_ENDPOINT)
    fun getPaymentIntent(@Path("customer_id") customerId: Int) : Call<String>
}