package com.gokhandroid.demoecommerce.ui.paymentinfo

import android.os.Bundle
import android.view.View
import com.gokhandroid.demoecommerce.AESUtil.decrypt
import com.gokhandroid.demoecommerce.base.BaseFragment
import com.gokhandroid.demoecommerce.databinding.FragmentPaymentInfoBinding
import java.text.SimpleDateFormat


class PaymentInfoFragment : BaseFragment<PaymentInfoViewModel, FragmentPaymentInfoBinding>() {

    override fun getViewBinding(): FragmentPaymentInfoBinding =
        FragmentPaymentInfoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val arguments = PaymentInfoFragmentArgs.fromBundle(it)
            viewModel.orderId = arguments.orderIdArg
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {

            success.observe(viewLifecycleOwner) { order ->
                binding.orderDate.text =
                    SimpleDateFormat("dd.MM.yyyy").format(order.createdDate)
                binding.amount.text =
                    "${String.format("%.2f", order.totalAmount).toDouble()} TL"
                binding.orderId.text = order.orderId.toString()
                val cardNo = decrypt(order.cardNo)!!
                binding.cardNumber.text = cardNo.replaceRange(7, 12, "*".repeat(12 - 1))
                binding.paymentSuccessful.setOnClickListener {
                    navigateWithAction(PaymentInfoFragmentDirections.actionPaymentInfoToOrder())
                }
            }

            getOrder(orderId)
        }
    }

}