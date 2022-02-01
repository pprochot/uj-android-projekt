package com.github.pprochot.uj.android.fragments

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.ProductsInOrderAdapter
import com.github.pprochot.uj.android.realmmodels.Cart
import com.github.pprochot.uj.android.realmmodels.Order
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class OrderInfoFragment : Fragment(R.layout.fragment_order_info) {

    private lateinit var recyclerView: RecyclerView
    private val args: OrderInfoFragmentArgs by navArgs()

    @Inject
    lateinit var realm: Realm

    override fun onStart() {
        super.onStart()

        val order = realm.where(Order::class.java).equalTo("id", args.orderId).findFirst()
        val products = order?.products?.toList() ?: emptyList()

        recyclerView = view?.findViewById(R.id.rv_products_in_order)!!
        val adapter = ProductsInOrderAdapter(requireContext(), products)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        view?.findViewById<TextView>(R.id.text_order_cost)?.text = order?.cost?.value.toString()
        view?.findViewById<TextView>(R.id.text_order_date)?.text = order?.date.toString()
    }
}