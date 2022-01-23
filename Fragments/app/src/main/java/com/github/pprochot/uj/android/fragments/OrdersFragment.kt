package com.github.pprochot.uj.android.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.OrdersAdapter
import com.github.pprochot.uj.android.realmmodels.Order
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var realm: Realm

    override fun onStart() {
        super.onStart()

        val id = 3
        val orders = realm.where(Order::class.java).equalTo("customer.id", id)
            .findAll()

        recyclerView = view?.findViewById(R.id.rv_orders)!!
        val adapter = OrdersAdapter(requireContext(), orders)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}