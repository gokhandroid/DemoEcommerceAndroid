package com.gokhandroid.demoecommerce.data.entity

import androidx.room.*
import com.gokhandroid.demoecommerce.ConsRoomDB
import com.gokhandroid.demoecommerce.base.DateConverter
import java.util.*


@Entity(tableName = ConsRoomDB.TBL_ORDER)
@TypeConverters(DateConverter::class)
data class Order(
    @PrimaryKey val orderId: Int,
    @ColumnInfo(name = ConsRoomDB.col_cardNo) var cardNo: String,
    @ColumnInfo(name = ConsRoomDB.col_totalAmount) val totalAmount: Double,
    @ColumnInfo(name = ConsRoomDB.col_isCanceled) var isCanceled: Boolean,
    @ColumnInfo(name = ConsRoomDB.col_createdDate) val createdDate: Date
)


@Entity(tableName = ConsRoomDB.TBL_ORDERWITHPRODUCT, primaryKeys = [ConsRoomDB.col_orderId, ConsRoomDB.col_productId])
class OrderWithProducts(
    val orderId: Int,
    val productId: Int,
    val quantity: Int
)

data class OrderProductPair(
    @Embedded
    var order: Order,
    @Relation(
        parentColumn = ConsRoomDB.col_orderId,
        entity = Product::class,
        entityColumn = ConsRoomDB.col_productId,
        associateBy = Junction(
            value = OrderWithProducts::class,
            parentColumn = ConsRoomDB.col_orderId,
            entityColumn = ConsRoomDB.col_productId
        )
    )
    var product: List<Product>,

    @Relation(
        parentColumn = ConsRoomDB.col_orderId,
        entityColumn = ConsRoomDB.col_orderId
    )
    var orderWithProduct: List<OrderWithProducts>
)