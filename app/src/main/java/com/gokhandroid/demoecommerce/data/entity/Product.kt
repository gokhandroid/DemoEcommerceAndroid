package com.gokhandroid.demoecommerce.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gokhandroid.demoecommerce.ConsRoomDB

@Entity(tableName = ConsRoomDB.TBL_PRODUCT)
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int,
    @ColumnInfo(name = ConsRoomDB.col_productName) val productName: String,
    @ColumnInfo(name = ConsRoomDB.col_productPrice) val productPrice: Double
)
