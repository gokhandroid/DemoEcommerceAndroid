package com.gokhandroid.demoecommerce.data.entity

import androidx.room.*
import com.gokhandroid.demoecommerce.ConsRoomDB
import com.gokhandroid.demoecommerce.base.DateConverter
import java.io.Serializable
import java.util.*

@Entity(tableName = ConsRoomDB.TBL_BASKET)
@TypeConverters(DateConverter::class)
data class Basket(
    @PrimaryKey val basketId: Int,
    @ColumnInfo(name = ConsRoomDB.col_createdDate) val createdDate: Date
)

@Entity(tableName = ConsRoomDB.TBL_BASKETWITHPRODUCT, primaryKeys = [ConsRoomDB.col_basketId, ConsRoomDB.col_productId])
class BasketWithProducts(
    val basketId: Int,
    val productId: Int,
    val quantity: Int,
    val amount: Double
)

data class BasketProductPair(
    @Embedded
    var basket: Basket,
    @Relation(
        parentColumn = ConsRoomDB.col_basketId,
        entity = Product::class,
        entityColumn = ConsRoomDB.col_productId,
        associateBy = Junction(
            value = BasketWithProducts::class,
            parentColumn = ConsRoomDB.col_basketId,
            entityColumn = ConsRoomDB.col_productId
        )
    )
    var product: List<Product>,

    @Relation(
        parentColumn = ConsRoomDB.col_basketId,
        entityColumn = ConsRoomDB.col_basketId
    )
    var basketWithProduct: List<BasketWithProducts>
) : Serializable