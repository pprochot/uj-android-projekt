package com.github.pprochot.uj.android.fragments

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.ProductsInCartAdapter
import com.github.pprochot.uj.android.realmmodels.Cart
import com.github.pprochot.uj.android.realmmodels.Product
import com.github.pprochot.uj.android.viewmodels.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var realm: Realm

    private var cart: Cart? = null

    private val userInfoViewModel: UserInfoViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cart = realm.where(Cart::class.java).equalTo("owner.id", userInfoViewModel.userId)
            .findFirst()
    }

    override fun onStart() {
        super.onStart()

        val products = cart?.products?.toList() ?: emptyList()

        recyclerView = view?.findViewById(R.id.rv_products_in_cart)!!
        val adapter = ProductsInCartAdapter(requireContext(), products)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}