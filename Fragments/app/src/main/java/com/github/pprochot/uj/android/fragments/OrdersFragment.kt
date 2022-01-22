package com.github.pprochot.uj.android.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.OrdersAdapter

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