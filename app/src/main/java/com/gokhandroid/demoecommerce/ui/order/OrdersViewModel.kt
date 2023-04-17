package com.gokhandroid.demoecommerce.ui.order

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gokhandroid.demoecommerce.base.BaseViewModel
import com.gokhandroid.demoecommerce.data.entity.Order
import com.gokhandroid.demoecommerce.data.entity.OrderProductPair
import com.gokhandroid.demoecommerce.data.repository.IOrderRepository
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrdersViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) :
    BaseViewModel() {

    var success: MutableLiveData<List<OrderProductPair>> = MutableLiveData()
    var successDelete: MutableLiveData<Unit> = MutableLiveData()
    var successUpdate: MutableLiveData<Unit> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun fetchOrder() {
        applyDisposable(
            orderRepository.getOrderProducts()
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(object : DisposableSingleObserver<List<OrderProductPair>>() {
                    override fun onSuccess(t: List<OrderProductPair>) {
                        Log.i("GetOrder", "")
                        success.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.e("GetOrder", e.stackTraceToString())
                        error.postValue(e)
                    }
                })
        )
    }

    fun updateOrder(order: Order) {
        applyDisposable(
            orderRepository.updateOrderStatus(order)
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(object : DisposableSingleObserver<Int>() {
                    override fun onSuccess(t: Int) {
                        successUpdate.postValue(Unit)
                        Log.i("Update", "")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("UpdateOrder", e.stackTraceToString())
                        error.postValue(e)
                    }
                })
        )
    }

}