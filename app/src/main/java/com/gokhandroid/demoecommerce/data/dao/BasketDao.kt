package com.gokhandroid.demoecommerce.data.dao

import androidx.room.*
import com.gokhandroid.demoecommerce.ConsRoomDB
import com.gokhandroid.demoecommerce.data.entity.Basket
import com.gokhandroid.demoecommerce.data.entity.BasketProductPair
import com.gokhandroid.demoecommerce.data.entity.BasketWithProducts
import io.reactivex.Single
import java.util.*


@Dao
abstract class BasketDao {

    @Query("Select * from ${ConsRoomDB.TBL_BASKET} WHERE `basketId`= :key LIMIT 1")
    abstract fun get(key: String): Single<Basket>

    @Query("Select * from ${ConsRoomDB.TBL_BASKET}")
    abstract fun getAll(): List<Basket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertWithProducts(join: BasketWithProducts)

    @Insert()
    abstract fun insert(basket: Basket)

    @Transaction
    @Query("SELECT * FROM ${ConsRoomDB.TBL_BASKET} LIMIT 1")
    abstract fun getBasket(): Single<BasketProductPair>

    @Query("SELECT * FROM ${ConsRoomDB.TBL_BASKETWITHPRODUCT} WHERE `productId`= :key")
    abstract fun getBasketProduct(key: Int): Single<BasketWithProducts>

    @Query("DELETE FROM ${ConsRoomDB.TBL_BASKETWITHPRODUCT}")
    abstract fun deleteAllBasketProducts()

    @Query("DELETE FROM ${ConsRoomDB.TBL_BASKETWITHPRODUCT} WHERE `productId`= :key")
    abstract fun deleteBasketProducts(key: Int) : Single<Int>

    @Transaction
    open fun insertTransaction(join: BasketWithProducts) {
        val size = getAll().size
        if (size == 0) {
            insert(Basket(1, Calendar.getInstance().time))
        }
        insertWithProducts(join)
    }
}
