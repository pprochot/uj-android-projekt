package com.github.pprochot.uj.android.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.realmmodels.Product
import java.math.BigDecimal

class ProductsAdapter(private val context: Context, private val products: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameView: TextView = itemView.findViewById(R.id.product_row_name)
        val categoryView: TextView = itemView.findViewById(R.id.product_row_category)
        val descriptionView: TextView = itemView.findViewById(R.id.product_row_descr)
        val cost: TextView = itemView.findViewById(R.id.product_row_cost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_product_add, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nameView.text = product.name
        holder.categoryView.text = product.category?.name
        holder.descriptionView.text = product.description
        holder.cost.text = product.cost?.value.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
