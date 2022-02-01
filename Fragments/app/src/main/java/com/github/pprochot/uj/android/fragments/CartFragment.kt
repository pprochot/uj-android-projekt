package com.github.pprochot.uj.android.fragments

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.ProductsInCartAdapter
import com.github.pprochot.uj.android.domain.request.CartRequest
import com.github.pprochot.uj.android.domain.request.OrderRequest
import com.github.pprochot.uj.android.domain.response.CartResponse
import com.github.pprochot.uj.android.domain.response.OrderResponse
import com.github.pprochot.uj.android.mappers.CartMapper
import com.github.pprochot.uj.android.mappers.OrderMapper
import com.github.pprochot.uj.android.realmmodels.Cart
import com.github.pprochot.uj.android.services.CartService
import com.github.pprochot.uj.android.services.OrderService
import com.github.pprochot.uj.android.services.StripeService
import com.github.pprochot.uj.android.viewmodels.UserInfoViewModel
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsInCartAdapter
    private var paymentIntentClientSecret: String? = null
    private lateinit var paymentSheet: PaymentSheet
    private val userInfoViewModel: UserInfoViewModel by activityViewModels()

    @Inject
    lateinit var realm: Realm

    @Inject
    lateinit var cartService: CartService

    @Inject
    lateinit var orderService: OrderService

    @Inject
    lateinit var stripeService: StripeService

    @Inject
    lateinit var cartMapper: CartMapper

    @Inject
    lateinit var orderMapper: OrderMapper

    private var cart: Cart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cart = realm.where(Cart::class.java).equalTo("id", userInfoViewModel.cartId)
            .findFirst()

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        fetchPaymentIntent()
    }

    override fun onStart() {
        super.onStart()

        val products = cart?.products?.toList() ?: emptyList()

        recyclerView = view?.findViewById(R.id.rv_products_in_cart)!!
        productsAdapter = ProductsInCartAdapter(requireContext(), products.toMutableList())
        recyclerView.adapter = productsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val finalizeOrderButton = view?.findViewById<Button>(R.id.button_finalize_order)
        finalizeOrderButton?.setOnClickListener {
            if (paymentIntentClientSecret != null) {
                val configuration = PaymentSheet.Configuration("UJ Android Project")
                paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret!!, configuration)
            }
        }
    }

    private fun fetchPaymentIntent() {
        if (cart != null && !cart!!.products.isEmpty()) {
            stripeService.getPaymentIntent(userInfoViewModel.userId!!)
                .enqueue(FinalizeOrderCallback())
        } else {
            Toast.makeText(requireContext(), "Empty cart.", Toast.LENGTH_LONG).show()
            realizeOrder()
        }
    }

    private fun onPaymentSheetResult(paymentResult: PaymentSheetResult) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                Toast.makeText(requireContext(), "Payment completed.", Toast.LENGTH_LONG).show()
                realizeOrder()
            }
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(requireContext(), "Payment canceled!", Toast.LENGTH_LONG).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(
                    requireContext(),
                    "Payment failed. ${paymentResult.error.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun realizeOrder() {
        val products = cart!!.products.map { product ->
            product.id
        }

        orderService.create(OrderRequest(userInfoViewModel.userId!!, products))
            .enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val order = orderMapper.map(response.body()!!)
                        realm.executeTransactionAsync() {
                            it.insertOrUpdate(order)
                        }
                        updateCart()
                    } else {
                        Log.e("Failed to realize order!", response.message())
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Log.e("Failed to realize order!", t.message.toString())
                }
            })
    }

    private fun updateCart() {
        cartService.update(cart!!.id, CartRequest(userInfoViewModel.userId!!, emptyList()))
            .enqueue(object : Callback<CartResponse> {
                override fun onResponse(
                    call: Call<CartResponse>,
                    response: Response<CartResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        realm.executeTransactionAsync {
                            it.where(Cart::class.java).equalTo("owner.id", userInfoViewModel.userId)
                                .findFirst()
                                ?.products = RealmList()
                        }
                        productsAdapter.products = emptyList()
                        productsAdapter.notifyDataSetChanged()
                    } else {
                        Log.e("Failed to clear cart.", response.message())
                        """
                            I was working on es-product-detail-service, how to exactly test rest cluster messaging.
                            And also checking some PR's.
                        """.trimIndent()
                    }
                }

                override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                    Log.e("Failed to clear cart!", t.message.toString())
                }
            })
    }

    private inner class FinalizeOrderCallback : Callback<String> {
        override fun onResponse(
            call: Call<String>,
            response: Response<String>
        ) {
            if (response.isSuccessful && response.body() != null) {
                runBlocking {
                    withContext(Dispatchers.IO) {
                        paymentIntentClientSecret = response.body()!!
                    }
                }
            } else {
                Log.e("stripe_fail", response.message())
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            Log.e("stripe", t.message.toString())
        }
    }
}