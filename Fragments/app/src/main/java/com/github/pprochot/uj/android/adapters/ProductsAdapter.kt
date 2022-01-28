package com.github.pprochot.uj.android.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.realmmodels.Product
import com.github.pprochot.uj.android.services.CartService
import java.math.BigDecimal

class ProductsAdapter(private val context: Context, private val products: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    val productsToAdd: MutableList<Int> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var id: Int? = null
        val nameView: TextView = itemView.findViewById(R.id.product_row_name)
        val categoryView: TextView = itemView.findViewById(R.id.product_row_category)
        val descriptionView: TextView = itemView.findViewById(R.id.product_row_descr)
        val cost: TextView = itemView.findViewById(R.id.product_row_cost)

        init {
            itemView.findViewById<ImageButton>(R.id.button_add_product_to_cart)
                .setOnClickListener {
                    if (id != null) {
                        productsToAdd.add(id!!)
                    }
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_product_add, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.id = product.id
        holder.nameView.text = product.name
        holder.categoryView.text = product.category?.name
        holder.descriptionView.text = product.description
        holder.cost.text = product.cost?.value.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
