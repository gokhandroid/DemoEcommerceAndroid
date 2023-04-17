package com.gokhandroid.demoecommerce.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gokhandroid.demoecommerce.data.dao.BasketDao
import com.gokhandroid.demoecommerce.data.dao.OrderDao
import com.gokhandroid.demoecommerce.data.dao.ProductDao
import com.gokhandroid.demoecommerce.data.entity.*

@Database(
    entities = [Product::class, Basket::class, BasketWithProducts::class, Order::class, OrderWithProducts::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun basketDao(): BasketDao
    abstract fun orderDao(): OrderDao
}