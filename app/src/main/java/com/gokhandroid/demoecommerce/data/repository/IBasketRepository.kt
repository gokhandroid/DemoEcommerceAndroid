package com.gokhandroid.demoecommerce.data.repository

import com.gokhandroid.demoecommerce.data.entity.BasketProductPair
import com.gokhandroid.demoecommerce.data.entity.BasketWithProducts
import io.reactivex.Completable
import io.reactivex.Single

interface IBasketRepository {
    fun getBasketProducts(): Single<BasketProductPair>
    fun getBasketProduct(productId: Int): Single<BasketWithProducts>
    fun insertBasketProduct(basketWithProducts: BasketWithProducts): Completable
    fun deleteAllBasketProducts() : Completable
    fun deleteBasketProduct(productId: Int) : Single<Int>
}