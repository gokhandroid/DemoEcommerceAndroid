package com.gokhandroid.demoecommerce.ui.basket

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gokhandroid.demoecommerce.base.BaseViewModel
import com.gokhandroid.demoecommerce.data.entity.BasketProductPair
import com.gokhandroid.demoecommerce.data.repository.IBasketRepository
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BasketViewModel @Inject constructor(
    private val basketRepository: IBasketRepository) :
    BaseViewModel() {

    var success: MutableLiveData<BasketProductPair> = MutableLiveData()
    var successDelete: MutableLiveData<Unit> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun fetchBasket() {
        applyDisposable(
            basketRepository.getBasketProducts()
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(object : DisposableSingleObserver<BasketProductPair>() {
                    override fun onSuccess(t: BasketProductPair) {
                        Log.i("GetBasket", t.basket.basketId.toString())
                        success.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.e("GetBasket", e.stackTraceToString())
                        error.postValue(e)
                    }
                })
        )
    }

    fun deleteBasketProduct(productId: Int) {
        applyDisposable(
            basketRepository.deleteBasketProduct(productId)
                .subscribeOn(Schedulers.trampoline())
                .subscribeWith(object : DisposableSingleObserver<Int>() {
                    override fun onSuccess(t: Int) {
                        successDelete.postValue(Unit)
                        Log.i("DeleteBasket", "")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("DeleteBasket", e.stackTraceToString())
                        error.postValue(e)
                    }
                })
        )
    }
}