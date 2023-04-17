package com.gokhandroid.demoecommerce.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gokhandroid.demoecommerce.base.BaseViewModel
import com.gokhandroid.demoecommerce.data.entity.Product
import com.gokhandroid.demoecommerce.data.repository.IBasketRepository
import com.gokhandroid.demoecommerce.data.repository.IProductRepository
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val basketRepository: IBasketRepository) :
    BaseViewModel() {

    var success: MutableLiveData<Unit> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun deleteBasketProducts() {
        applyDisposable(
            basketRepository.deleteAllBasketProducts()
                .subscribeOn(Schedulers.trampoline())
                .doOnComplete {
                    success.postValue(Unit)
                }
                .doOnError {
                    Log.e("DeleteAllBasket", it.stackTraceToString())
                    error.postValue(it)
                }
                .subscribe()
        )
    }

}