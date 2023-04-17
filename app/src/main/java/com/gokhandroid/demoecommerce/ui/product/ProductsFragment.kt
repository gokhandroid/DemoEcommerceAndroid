package com.gokhandroid.demoecommerce.ui.product

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokhandroid.demoecommerce.base.BaseFragment
import com.gokhandroid.demoecommerce.data.entity.BasketWithProducts
import com.gokhandroid.demoecommerce.databinding.FragmentProductsBinding
import javax.inject.Inject

class ProductsFragment : BaseFragment<ProductsViewModel, FragmentProductsBinding>() {

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun getViewBinding(): FragmentProductsBinding =
        FragmentProductsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            binding.recycleProductList.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleProductList.adapter = productsAdapter
            productsAdapter.setOnItemCLickListener(object : OnItemClickListener {
                override fun onAddButtonClicked(model: ProductModel, position: Int) {

                    val product = getBasketProduct(model.product.productId)
                    var currentQuantity = 0
                    if (product.quantity != 0) {
                        currentQuantity = product.quantity
                        if ((currentQuantity + model.quantity) > 5) {
                            Toast.makeText(
                                requireContext(),
                                "You can add up to 5 of the same product to basket",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                    }

                    model.quantity = (currentQuantity + model.quantity)
                    val amount = model.quantity * model.product.productPrice
                    addToBasket(
                        BasketWithProducts(
                            BASKET_ID,
                            model.product.productId,
                            model.quantity,
                            amount
                        )
                    )

                    model.quantity = 1
                    productsAdapter.notifyItemChanged(position)
                }

                override fun onPlusMinusClicked(id: Int) {
                    productsAdapter.notifyItemChanged(id)
                }

            })

            successBasketProduct.observe(viewLifecycleOwner) {

            }

            success.observe(viewLifecycleOwner) {
                productsAdapter.setList(it)
            }

            error.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it.stackTraceToString(), Toast.LENGTH_LONG).show()
            }
            fetchProducts()
        }
    }
}