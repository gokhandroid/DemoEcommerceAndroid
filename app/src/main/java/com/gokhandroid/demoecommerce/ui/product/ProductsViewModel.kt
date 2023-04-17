package com.gokhandroid.demoecommerce.ui.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.gokhandroid.demoecommerce.base.BaseViewModel
import com.gokhandroid.demoecommerce.data.entity.BasketWithProducts
import com.gokhandroid.demoecommerce.data.entity.Product
import com.gokhandroid.demoecommerce.data.repository.IBasketRepository
import com.gokhandroid.demoecommerce.data.repository.IProductRepository
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productRepository: IProductRepository,
    private val basketRepository: IBasketRepository
) :
    BaseViewModel() {

    val BASKET_ID = 1

    var success: MutableLiveData<List<ProductModel>> = MutableLiveData()
    var successBasketProduct: MutableLiveData<BasketWithProducts> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun fetchProducts() {
        applyDisposable(
            productRepository.getProducts().subscribeOn(Schedulers.trampoline())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(t: List<Product>) {
                        success.postValue(t.map {
                            ProductModel(it, 1)
                        })
                    }

                    override fun onError(e: Throwable) {
                        Log.e("GetProducts", e.stackTraceToString())
                        error.postValue(e)
                    }
                })
        )
    }

    fun addToBasket(basketWithProducts: BasketWithProducts) {
        applyDisposable(basketRepository.insertBasketProduct(basketWithProducts)
            .subscribeOn(Schedulers.trampoline())
            .doOnError {
                Log.e("InsertBasket", it.stackTraceToString())
                error.postValue(it)
            }
            .subscribe()
        )
    }

    fun getBasketProduct(productId: Int): BasketWithProducts {
        return basketRepository.getBasketProduct(productId)
            .onErrorResumeNext {
                if (it is EmptyResultSetException) Single.just(BasketWithProducts(0, 0, 0, 0.0))
                else Single.error(it)
            }.subscribeOn(Schedulers.io())
            .blockingGet()
    }
}