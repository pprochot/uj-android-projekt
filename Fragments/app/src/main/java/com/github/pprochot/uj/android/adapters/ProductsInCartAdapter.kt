package com.github.pprochot.uj.android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import java.math.BigDecimal

class ProductsInCartAdapter(private var context: Context) :
    RecyclerView.Adapter<ProductsInCartAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameView: TextView = itemView.findViewById(R.id.product_remove_row_name)
        val categoryView: TextView = itemView.findViewById(R.id.product_remove_row_category)
        val descriptionView: TextView = itemView.findViewById(R.id.product_remove_row_descr)
        val cost: TextView = itemView.findViewById(R.id.product_remove_row_cost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_product_remove, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = "Heelu"
        holder.categoryView.text = "Ctg"
        holder.descriptionView.text = "Heelu Descr"
        holder.cost.text = BigDecimal.ONE.toString()
    }

    override fun getItemCount(): Int {
        return 3
    }
}
