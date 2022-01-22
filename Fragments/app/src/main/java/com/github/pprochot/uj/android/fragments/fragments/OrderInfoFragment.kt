package com.github.pprochot.uj.android.fragments.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.fragments.R
import com.github.pprochot.uj.android.fragments.adapters.ProductsInOrderAdapter

class OrderInfoFragment : Fragment(R.layout.fragment_order_info) {

    private lateinit var recyclerView: RecyclerView

    override fun onStart() {
        super.onStart()

        recyclerView = view?.findViewById(R.id.rv_products_in_order)!!
        val adapter = ProductsInOrderAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}