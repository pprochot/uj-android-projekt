package com.github.pprochot.uj.android.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.ProductsAdapter
import com.github.pprochot.uj.android.domain.request.CartRequest
import com.github.pprochot.uj.android.domain.response.CartResponse
import com.github.pprochot.uj.android.domain.response.UserResponse
import com.github.pprochot.uj.android.mappers.CartMapper
import com.github.pprochot.uj.android.mappers.UserMapper
import com.github.pprochot.uj.android.realmmodels.Cart
import com.github.pprochot.uj.android.realmmodels.Category
import com.github.pprochot.uj.android.realmmodels.Product
import com.github.pprochot.uj.android.services.CartService
import com.github.pprochot.uj.android.viewmodels.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var realm: Realm

    @Inject
    lateinit var cartService: CartService

    @Inject
    lateinit var cartMapper: CartMapper

    private var cart: Cart? = null

    private val userInfoViewModel: UserInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cart = realm.where(Cart::class.java).equalTo("owner.id", userInfoViewModel.userId)
            .findFirst()
    }

    override fun onStart() {
        super.onStart()


        val products = realm.where(Product::class.java)
            .findAll()
            .toList()

        recyclerView = view?.findViewById(R.id.rv_products)!!
        productsAdapter = ProductsAdapter(requireContext(), products)
        recyclerView.adapter = productsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onStop() {
        super.onStop()
        val productsToAdd = productsAdapter.productsToAdd
        val currentProducts = cart!!.products.toList().map { it.id }.toMutableList()

        currentProducts.addAll(productsToAdd)

        cartService.update(cart!!.id, CartRequest(userInfoViewModel.userId!!, currentProducts))
            .enqueue(UpdateCartCallback())
    }

    private inner class UpdateCartCallback : Callback<CartResponse> {

        override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
            if (response.isSuccessful && response.body() != null) {
                val cart = cartMapper.map(response.body()!!)
                realm.executeTransaction {
                    it.insertOrUpdate(cart)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong with saving products! Try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<CartResponse>, t: Throwable) {
            Toast.makeText(
                requireContext(),
                "Something went wrong with saving products! Try again later.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}