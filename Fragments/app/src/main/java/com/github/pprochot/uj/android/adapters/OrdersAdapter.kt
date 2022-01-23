package com.github.pprochot.uj.android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.pprochot.uj.android.R
import com.github.pprochot.uj.android.fragments.OrdersFragmentDirections
import com.github.pprochot.uj.android.realmmodels.Order

class OrdersAdapter(private val context: Context, private val orders: List<Order>) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var orderId: Long? = null
        val dateView: TextView = itemView.findViewById(R.id.order_date)
        val orderCost: TextView = itemView.findViewById(R.id.order_cost)

        init {
            itemView.setOnClickListener {
                if (orderId != null) {
                    val toOrderInfo =
                        OrdersFragmentDirections.actionOrdersFragmentToOrderInfoFragment(orderId!!)
                    it.findNavController().navigate(toOrderInfo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.orderId = order.id.toLong()
        holder.dateView.text = order.date.toString() //TODO incorrect date
        holder.orderCost.text = order.cost?.dbValue
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
