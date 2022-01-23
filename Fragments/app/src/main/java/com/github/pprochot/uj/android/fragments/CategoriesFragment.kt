package com.github.pprochot.uj.android.fragments

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.adapters.CategoriesAdapter
import com.github.pprochot.uj.android.realmmodels.Category
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var realm: Realm

    override fun onStart() {
        super.onStart()

        val categories = realm.where(Category::class.java)
            .findAll()
            .toList()

        recyclerView = view?.findViewById(R.id.rv_categories)!!
        val adapter = CategoriesAdapter(requireContext(), categories)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}