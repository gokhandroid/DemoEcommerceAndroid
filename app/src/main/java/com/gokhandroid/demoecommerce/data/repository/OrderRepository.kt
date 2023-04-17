package com.gokhandroid.demoecommerce.data.repository

import com.gokhandroid.demoecommerce.data.AppDatabase
import com.gokhandroid.demoecommerce.data.entity.Order
import com.gokhandroid.demoecommerce.data.entity.OrderProductPair
import com.gokhandroid.demoecommerce.data.entity.OrderWithProducts
import io.reactivex.Completable
import io.reactivex.Single

class OrderRepository(private val db: AppDatabase) : IOrderRepository {

    override fun getOrderProducts(): Single<List<OrderProductPair>> {
        return db.orderDao().getAllOrder()
    }

    override fun getOrder(orderId: Int): Single<Order> {
        return db.orderDao().get(orderId)
    }

    override fun insertOrderProduct(
        order: Order,
        orderWithProducts: List<OrderWithProducts>
    ): Completable {
        return Completable.fromAction { db.orderDao().insertTransaction(order, orderWithProducts) }
    }

    override fun updateOrderStatus(order: Order): Single<Int> {
        return db.orderDao().update(order)
    }

    override fun deleteAllOrderProducts(): Completable {
        return Completable.fromAction { db.orderDao().deleteAllOrderProducts() }
    }

    override fun deleteOrderProduct(productId: Int): Single<Int> {
        return db.orderDao().deleteOrderProducts(productId)
    }

}