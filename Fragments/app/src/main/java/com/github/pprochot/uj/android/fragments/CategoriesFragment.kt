package com.github.pprochot.uj.android.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.CategoriesAdapter

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var recyclerView: RecyclerView

    override fun onStart() {
        super.onStart()

        recyclerView = view?.findViewById(R.id.rv_categories)!!
        val adapter = CategoriesAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}