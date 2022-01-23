package com.github.pprochot.uj.android.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.ProductsAdapter
import com.github.pprochot.uj.android.realmmodels.Category
import com.github.pprochot.uj.android.realmmodels.Product
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var realm: Realm

    override fun onStart() {
        super.onStart()


        val products = realm.where(Product::class.java)
            .findAll()
            .toList()

        recyclerView = view?.findViewById(R.id.rv_products)!!
        val adapter = ProductsAdapter(requireContext(), products)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}