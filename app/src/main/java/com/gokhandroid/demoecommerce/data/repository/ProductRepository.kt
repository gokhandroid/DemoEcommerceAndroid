package com.gokhandroid.demoecommerce.data.repository

import com.gokhandroid.demoecommerce.data.AppDatabase
import com.gokhandroid.demoecommerce.data.entity.Product
import io.reactivex.Single

class ProductRepository(private val db: AppDatabase) : IProductRepository {

    override fun getProduct(key: String): Single<Product> {
        return db.productDao().get(key)
    }

    override fun getProducts(): Single<List<Product>> {
        return db.productDao().getAll()
    }
}