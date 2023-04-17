package com.gokhandroid.demoecommerce.ui.order

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokhandroid.demoecommerce.base.BaseFragment
import com.gokhandroid.demoecommerce.data.entity.OrderProductPair
import com.gokhandroid.demoecommerce.databinding.FragmentOrdersBinding
import javax.inject.Inject

class OrdersFragment : BaseFragment<OrdersViewModel, FragmentOrdersBinding>() {

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    override fun getViewBinding(): FragmentOrdersBinding =
        FragmentOrdersBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            binding.recycleOrderList.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleOrderList.adapter = ordersAdapter
            ordersAdapter.setOnItemCLickListener(object :
                OnItemClickListener {
                override fun onRemoveClicked(item: OrderProductPair) {
                    showAlertDialog(item)
                }
            })

            successDelete.observe(viewLifecycleOwner) {
                fetchOrder()
            }

            successUpdate.observe(viewLifecycleOwner) {
                fetchOrder()
            }

            success.observe(viewLifecycleOwner) {
                ordersAdapter.setList(it)
            }

            error.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it.stackTraceToString(), Toast.LENGTH_LONG).show()
            }

            fetchOrder()
        }
    }

    private fun showAlertDialog(item: OrderProductPair) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Warning")
        builder.setMessage("Are you sure cancel order?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            item.order.isCanceled = true
            viewModel.updateOrder(item.order)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }
}