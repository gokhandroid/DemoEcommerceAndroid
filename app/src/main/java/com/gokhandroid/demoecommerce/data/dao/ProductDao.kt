package com.gokhandroid.demoecommerce.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.gokhandroid.demoecommerce.ConsRoomDB
import com.gokhandroid.demoecommerce.data.entity.Product
import io.reactivex.Single


@Dao
abstract class ProductDao {

    @Query("Select * from ${ConsRoomDB.TBL_PRODUCT} WHERE `productId`= :key LIMIT 1")
    abstract fun get(key: String): Single<Product>

    @Query("Select * from ${ConsRoomDB.TBL_PRODUCT}")
    abstract fun getAll(): Single<List<Product>>
}