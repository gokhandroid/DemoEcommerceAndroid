package com.gokhandroid.demoecommerce.ui.payment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.gokhandroid.demoecommerce.AESUtil
import com.gokhandroid.demoecommerce.base.BaseFragment
import com.gokhandroid.demoecommerce.data.entity.Order
import com.gokhandroid.demoecommerce.data.entity.OrderWithProducts
import com.gokhandroid.demoecommerce.databinding.FragmentPaymentBinding
import com.gokhandroid.demoecommerce.ui.main.MainActivity
import java.util.*
import java.util.regex.Pattern


class PaymentFragment : BaseFragment<PaymentViewModel, FragmentPaymentBinding>() {

    override fun getViewBinding(): FragmentPaymentBinding =
        FragmentPaymentBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val arguments = PaymentFragmentArgs.fromBundle(it)
            viewModel.basketProductPair = arguments.paymentArg
            viewModel.totalAmount = arguments.paymentAmountArg.toDouble()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {

            binding.inputCardNo.addTextChangedListener {
                it?.let {
                    binding.iconVisa.isVisible = false
                    binding.iconMastercard.isVisible = false

                    if (it.isEmpty())
                        return@addTextChangedListener

                    if (it.length == 16) {
                        if (!checkValidCard(it.toString()))
                            return@addTextChangedListener
                        else {
                            if (checkVisaPattern(it.toString()))
                                binding.iconVisa.isVisible = true
                            else if (checkMasterCardPattern(it.toString()))
                                binding.iconMastercard.isVisible = true
                        }
                    }
                }
            }

            binding.proceedCheckout.setOnClickListener {

                if (!checkValidCard(binding.inputCardNo.text.toString()))
                    return@setOnClickListener

                if (binding.inputName.text.toString().isEmpty()) {
                    binding.inputName.error = "Please enter a valid name"
                    return@setOnClickListener
                }

                val month = binding.inputMonth.text.toString()
                val year = binding.inputYear.text.toString()
                if (month.isEmpty() || year.isEmpty() || month.toInt() > 12 || month.toInt() < 1 || year.toInt() < 23) {
                    Toast.makeText(
                        requireContext(),
                        "Please enter a valid month and year",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val orderId = Random().nextInt(Int.MAX_VALUE)
                var orderWithProducts = listOf<OrderWithProducts>()
                basketProductPair?.let {
                    orderWithProducts = it.basketWithProduct.map {
                        OrderWithProducts(orderId, it.productId, it.quantity)
                    }
                }
                val order = Order(
                    orderId,
                    AESUtil.encrypt(binding.inputCardNo.text.toString())!!,
                    totalAmount,
                    false,
                    Calendar.getInstance().time
                )

                createOrder(
                    order,
                    orderWithProducts
                )

                (activity as MainActivity).clearBasket()
                navigateWithAction(PaymentFragmentDirections.actionPaymentToPaymentInfo(orderId))
            }
        }
    }

    private fun checkValidCard(cardNo: String): Boolean {
        val isValid = checkLuhn(cardNo)
        if (!isValid) {
            binding.inputCardNo.error = "Please enter a valid card number"
            return false
        }

        return true
    }

    private fun checkLuhn(cardNo: String): Boolean {
        val nDigits = cardNo.length
        var nSum = 0
        var isSecond = false
        for (i in nDigits - 1 downTo 0) {
            var d = cardNo[i] - '0'
            if (isSecond) d *= 2

            nSum += d / 10
            nSum += d % 10
            isSecond = !isSecond
        }
        return nSum % 10 == 0
    }

    private fun checkVisaPattern(cardNo: String): Boolean {
        val visaRegex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?))|"
        return checkRegex(cardNo, visaRegex)
    }

    private fun checkMasterCardPattern(cardNo: String): Boolean {
        val masterCardRegex = "(?<mastercard>5[1-5][0-9]{14})|"
        return checkRegex(cardNo, masterCardRegex)
    }

    private fun checkRegex(cardNo: String, regex: String): Boolean {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(cardNo)
        return matcher.matches()
    }
}