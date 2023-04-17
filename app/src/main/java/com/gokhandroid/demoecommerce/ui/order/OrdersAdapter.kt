package com.gokhandroid.demoecommerce.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.gokhandroid.demoecommerce.data.entity.OrderProductPair
import com.gokhandroid.demoecommerce.databinding.ItemOrderLayoutBinding
import java.text.SimpleDateFormat

class OrdersAdapter :
    RecyclerView.Adapter<OrdersAdapter.BasketViewHolder>() {

    private var orders = listOf<OrderProductPair>()

    private var itemClickListener: OnItemClickListener? = null

    fun setList(orderList: List<OrderProductPair>) {
        orders = orderList
        notifyDataSetChanged()
    }

    fun setOnItemCLickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(
            ItemOrderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = orders[position]
        holder.bind(itemClickListener, item)
        return
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class BasketViewHolder(private val itemBinding: ItemOrderLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            clickListener: OnItemClickListener?,
            item: OrderProductPair
        ) {
            val productNames = StringBuilder()
            item.product.forEach {
                productNames.append(it.productName)
                productNames.append("\n")
            }
            itemBinding.nameProduct.text =
                productNames.removeRange(productNames.lastIndex, productNames.lastIndex + 1)
                    .toString()
            itemBinding.orderDate.text =
                SimpleDateFormat("dd.MM.yyyy").format(item.order.createdDate)
            itemBinding.amount.text =
                "${String.format("%.2f", item.order.totalAmount).toDouble()} TL"
            itemBinding.orderStatus.text = if (item.order.isCanceled) "Canceled" else "Approved"
            itemBinding.removeBasket.isVisible = !item.order.isCanceled
            itemBinding.removeBasket.setOnClickListener {
                clickListener?.onRemoveClicked(item)
            }
        }
    }
}

interface OnItemClickListener {
    fun onRemoveClicked(order: OrderProductPair)
}