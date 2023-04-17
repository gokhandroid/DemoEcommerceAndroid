package com.gokhandroid.demoecommerce.data.dao

import androidx.room.*
import com.gokhandroid.demoecommerce.ConsRoomDB
import com.gokhandroid.demoecommerce.data.entity.Order
import com.gokhandroid.demoecommerce.data.entity.OrderProductPair
import com.gokhandroid.demoecommerce.data.entity.OrderWithProducts
import io.reactivex.Single


@Dao
abstract class OrderDao {

    @Query("Select * FROM ${ConsRoomDB.TBL_ORDER} WHERE `orderId`= :key LIMIT 1")
    abstract fun get(key: Int): Single<Order>

    @Query("Select * from ${ConsRoomDB.TBL_ORDER}")
    abstract fun getAll(): List<Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertWithProducts(join: OrderWithProducts)

    @Insert()
    abstract fun insert(order: Order)

    @Update()
    abstract fun update(order: Order): Single<Int>

    @Transaction
    @Query("SELECT * FROM ${ConsRoomDB.TBL_ORDER} LIMIT 1")
    abstract fun getOrder(): Single<OrderProductPair>

    @Transaction
    @Query("SELECT * FROM ${ConsRoomDB.TBL_ORDER}")
    abstract fun getAllOrder(): Single<List<OrderProductPair>>

    @Query("SELECT * FROM ${ConsRoomDB.TBL_ORDERWITHPRODUCT} WHERE `productId`= :key")
    abstract fun getOrderProduct(key: Int): Single<OrderWithProducts>

    @Query("DELETE FROM ${ConsRoomDB.TBL_ORDERWITHPRODUCT}")
    abstract fun deleteAllOrderProducts()

    @Query("DELETE FROM ${ConsRoomDB.TBL_ORDERWITHPRODUCT} WHERE `productId`= :key")
    abstract fun deleteOrderProducts(key: Int): Single<Int>

    @Transaction
    open fun insertTransaction(order: Order, join: List<OrderWithProducts>) {
        insert(order)
        join.forEach {
            insertWithProducts(it)
        }
    }
}
