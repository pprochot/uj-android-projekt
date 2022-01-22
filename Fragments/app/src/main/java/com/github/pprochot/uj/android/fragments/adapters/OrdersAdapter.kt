package com.github.pprochot.uj.android.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.fragments.R
import com.github.pprochot.uj.android.fragments.fragments.OrdersFragmentDirections

class OrdersAdapter(private var context: Context) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateView: TextView = itemView.findViewById(R.id.order_date)
        val orderCost: TextView = itemView.findViewById(R.id.order_cost)

        init {
            val toOrderInfo = OrdersFragmentDirections.actionOrdersFragmentToOrderInfoFragment()
            itemView.setOnClickListener {
                it.findNavController().navigate(toOrderInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateView.text = "Date"
        holder.orderCost.text = "Cooost"
    }

    override fun getItemCount(): Int {
        return 7
    }
}
