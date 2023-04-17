package com.gokhandroid.demoecommerce.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ProductsAdapter :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var productList = listOf<ProductModel>()

    private var itemClickListener: OnItemClickListener? = null

    fun setList(list: List<ProductModel>) {
        productList = list
        notifyDataSetChanged()
    }

    fun setOnItemCLickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            com.gokhandroid.demoecommerce.databinding.ItemProductLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productList[position]
        holder.bind(itemClickListener, item, position)
        return
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(private val itemBinding: com.gokhandroid.demoecommerce.databinding.ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(clickListener: OnItemClickListener?, item: ProductModel, position: Int) {

            val context = itemBinding.root.context

            itemBinding.nameProduct.text = item.product.productName
            itemBinding.priceProduct.text = "${item.product.productPrice} TL"
            itemBinding.quantityProduct.text = item.quantity.toString()
            itemBinding.addBasket.setOnClickListener {
                clickListener?.onAddButtonClicked(item, position)
            }
            itemBinding.plusButton.setOnClickListener {
                val quantity = item.quantity
                if (quantity == 5) {
                    Toast.makeText(
                        context,
                        "You can add up to 5 of the same product to basket",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    item.quantity += 1
                }
                itemBinding.quantityProduct.text = item.quantity.toString()
                clickListener?.onPlusMinusClicked(position)
            }

            itemBinding.minusButton.setOnClickListener {
                val quantity = item.quantity
                if (quantity == 1) {

                } else {
                    item.quantity -= 1
                }
                itemBinding.quantityProduct.text = item.quantity.toString()
                clickListener?.onPlusMinusClicked(position)
            }
        }
    }
}

interface OnItemClickListener {
    fun onAddButtonClicked(product: ProductModel, position: Int)
    fun onPlusMinusClicked(id: Int)
}