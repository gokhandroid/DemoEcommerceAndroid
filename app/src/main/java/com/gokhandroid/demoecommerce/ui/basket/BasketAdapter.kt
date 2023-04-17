package com.gokhandroid.demoecommerce.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gokhandroid.demoecommerce.data.entity.BasketProductPair
import com.gokhandroid.demoecommerce.data.entity.BasketWithProducts
import com.gokhandroid.demoecommerce.data.entity.Product
import com.gokhandroid.demoecommerce.databinding.ItemBasketLayoutBinding

class BasketAdapter :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private var productList = listOf<Product>()
    private var basketWithProductList = listOf<BasketWithProducts>()

    private var itemClickListener: OnItemClickListener? = null

    fun setList(basket: BasketProductPair) {
        productList = basket.product
        basketWithProductList = basket.basketWithProduct
        notifyDataSetChanged()
    }

    fun setOnItemCLickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(
            ItemBasketLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val item = productList[position]
        val amount = basketWithProductList[position].amount
        val quantity = basketWithProductList[position].quantity
        holder.bind(itemClickListener, item, quantity, amount)
        return
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class BasketViewHolder(private val itemBinding: ItemBasketLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            clickListener: OnItemClickListener?,
            product: Product,
            quantity: Int,
            amount: Double
        ) {
            itemBinding.nameProduct.text = product.productName
            itemBinding.amount.text = "${String.format("%.2f", amount).toDouble()} TL"
            itemBinding.quantityProduct.text = "x$quantity"
            itemBinding.removeBasket.setOnClickListener {
                clickListener?.onDeleteClicked(product)
            }
        }
    }
}

interface OnItemClickListener {
    fun onDeleteClicked(product: Product)
}