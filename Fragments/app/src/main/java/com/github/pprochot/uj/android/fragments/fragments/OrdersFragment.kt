package com.github.pprochot.uj.android.fragments.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.fragments.R
import com.github.pprochot.uj.android.fragments.adapters.OrdersAdapter
import com.github.pprochot.uj.android.fragments.adapters.ProductsAdapter

class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private lateinit var recyclerView: RecyclerView

    override fun onStart() {
        super.onStart()

        recyclerView = view?.findViewById(R.id.rv_orders)!!
        val adapter = OrdersAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}