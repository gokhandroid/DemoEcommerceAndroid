package com.gokhandroid.demoecommerce.ui.payment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gokhandroid.demoecommerce.base.BaseViewModel
import com.gokhandroid.demoecommerce.data.entity.BasketProductPair
import com.gokhandroid.demoecommerce.data.entity.Order
import com.gokhandroid.demoecommerce.data.entity.OrderProductPair
import com.gokhandroid.demoecommerce.data.entity.OrderWithProducts
import com.gokhandroid.demoecommerce.data.repository.IOrderRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PaymentViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) :
    BaseViewModel() {

    var success: MutableLiveData<List<OrderProductPair>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    var basketProductPair: BasketProductPair? = null
    var totalAmount = 0.0

    fun createOrder(order: Order, orderWithProducts: List<OrderWithProducts>) {
        applyDisposable(orderRepository.insertOrderProduct(order, orderWithProducts)
            .subscribeOn(Schedulers.trampoline())
            .doOnError {
                Log.e("InsertOrder", it.stackTraceToString())
                error.postValue(it)
            }
            .subscribe()
        )
    }

}