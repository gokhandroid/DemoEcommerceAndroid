package com.gokhandroid.demoecommerce.ui.basket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokhandroid.demoecommerce.base.BaseFragment
import com.gokhandroid.demoecommerce.data.entity.BasketProductPair
import com.gokhandroid.demoecommerce.data.entity.Product
import com.gokhandroid.demoecommerce.databinding.FragmentBasketBinding
import javax.inject.Inject

class BasketFragment : BaseFragment<BasketViewModel, FragmentBasketBinding>() {

    @Inject
    lateinit var baskAdapter: BasketAdapter

    var totalAmount: Double = 0.0
    var basketProductPair: BasketProductPair? = null

    override fun getViewBinding(): FragmentBasketBinding =
        FragmentBasketBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            binding.recycleBasketList.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleBasketList.adapter = baskAdapter
            baskAdapter.setOnItemCLickListener(object :
                OnItemClickListener {
                override fun onDeleteClicked(product: Product) {

                    showAlertDialog(product)
                }
            })

            binding.makePayment.setOnClickListener {

                navigateWithAction(
                    BasketFragmentDirections.actionBasketToPayment(
                        basketProductPair!!,
                        totalAmount.toString()
                    )
                )
            }

            successDelete.observe(viewLifecycleOwner) {
                totalAmount = 0.0
                fetchBasket()
            }

            success.observe(viewLifecycleOwner) {
                baskAdapter.setList(it)
                basketProductPair = it
                it.basketWithProduct.forEach {
                    totalAmount += it.amount
                }
                binding.totalAmount.text =
                    "Total: ${String.format("%.1f", totalAmount).toDouble()} TL"
                binding.makePayment.isEnabled = totalAmount != 0.0
            }

            error.observe(viewLifecycleOwner) {

            }

            fetchBasket()
        }
    }

    private fun showAlertDialog(product: Product) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Warning")
        builder.setMessage("Are you sure delete product from basket?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.deleteBasketProduct(product.productId)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }
}