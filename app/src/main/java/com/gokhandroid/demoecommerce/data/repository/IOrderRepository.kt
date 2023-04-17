package com.gokhandroid.demoecommerce.data.repository

import com.gokhandroid.demoecommerce.data.entity.Order
import com.gokhandroid.demoecommerce.data.entity.OrderProductPair
import com.gokhandroid.demoecommerce.data.entity.OrderWithProducts
import io.reactivex.Completable
import io.reactivex.Single

interface IOrderRepository {
    fun getOrderProducts(): Single<List<OrderProductPair>>
    fun getOrder(orderId: Int): Single<Order>
    fun insertOrderProduct(order: Order, orderWithProducts: List<OrderWithProducts>): Completable
    fun updateOrderStatus(order: Order): Single<Int>
    fun deleteAllOrderProducts(): Completable
    fun deleteOrderProduct(productId: Int): Single<Int>
}