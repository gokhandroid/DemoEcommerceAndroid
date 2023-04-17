package com.gokhandroid.demoecommerce.ui.paymentinfo

import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gokhandroid.demoecommerce.base.BaseViewModel
import com.gokhandroid.demoecommerce.data.entity.Order
import com.gokhandroid.demoecommerce.data.repository.IOrderRepository
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class PaymentInfoViewModel @Inject constructor(
    private val orderRepository: IOrderRepository
) :
    BaseViewModel() {

    var success: MutableLiveData<Order> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()
    var orderId: Int = 0

    fun getOrder(orderId: Int) {
        applyDisposable(
            orderRepository.getOrder(orderId)
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(object : DisposableSingleObserver<Order>() {
                    override fun onSuccess(t: Order) {
                        success.postValue(t)
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