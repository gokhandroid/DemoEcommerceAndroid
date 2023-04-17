package com.gokhandroid.demoecommerce.data.repository

import com.gokhandroid.demoecommerce.data.entity.Product
import io.reactivex.Single

interface IProductRepository {
    fun getProduct(key: String): Single<Product>
    fun getProducts(): Single<List<Product>>
}