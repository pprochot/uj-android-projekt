package com.github.pprochot.uj.android.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.ProductsInCartAdapter
import com.github.pprochot.uj.android.realmmodels.Cart
import com.github.pprochot.uj.android.realmmodels.Product
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var realm: Realm

    override fun onStart() {
        super.onStart()

        val id = 3
        val products = realm.where(Cart::class.java).equalTo("owner.id", id)
            .findFirst()
            ?.products
            ?.toList() ?: emptyList()

        recyclerView = view?.findViewById(R.id.rv_products_in_cart)!!
        val adapter = ProductsInCartAdapter(requireContext(), products)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}