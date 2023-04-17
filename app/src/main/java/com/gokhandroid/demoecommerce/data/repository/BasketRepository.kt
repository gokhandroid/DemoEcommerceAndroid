package com.gokhandroid.demoecommerce.data.repository

import com.gokhandroid.demoecommerce.data.AppDatabase
import com.gokhandroid.demoecommerce.data.entity.BasketProductPair
import com.gokhandroid.demoecommerce.data.entity.BasketWithProducts
import io.reactivex.Completable
import io.reactivex.Single

class BasketRepository(private val db: AppDatabase) : IBasketRepository {

    override fun getBasketProducts(): Single<BasketProductPair> {
        return db.basketDao().getBasket()
    }

    override fun getBasketProduct(productId: Int): Single<BasketWithProducts> {
        return db.basketDao().getBasketProduct(productId)
    }

    override fun insertBasketProduct(basketWithProducts: BasketWithProducts): Completable {
        return Completable.fromAction { db.basketDao().insertTransaction(basketWithProducts) }
    }

    override fun deleteAllBasketProducts(): Completable {
        return Completable.fromAction { db.basketDao().deleteAllBasketProducts() }
    }

    override fun deleteBasketProduct(productId: Int): Single<Int> {
        return db.basketDao().deleteBasketProducts(productId)
    }

}