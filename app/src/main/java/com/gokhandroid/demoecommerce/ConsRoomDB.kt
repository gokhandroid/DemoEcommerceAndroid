package com.gokhandroid.demoecommerce


object ConsRoomDB {

    const val DB_NAME = "DEMO_DB"

    const val TBL_BASKETWITHPRODUCT = "BASKETWITHPRODUCTS"
    const val TBL_ORDERWITHPRODUCT = "ORDERWITHPRODUCT"
    const val TBL_BASKET = "BASKET"
    const val col_basketId = "basketId"
    const val col_productId = "productId"
    const val col_orderId = "orderId"
    const val col_productQuantity = "productQuantity"

    const val TBL_PRODUCT = "PRODUCT"
    const val col_productName = "productName"
    const val col_productPrice = "productPrice"

    const val TBL_ORDER = "ORDERS"
    const val col_cardNo = "cardNo"
    const val col_totalAmount = "totalAmount"
    const val col_isCanceled = "isCanceled"
    const val col_createdDate = "createdDate"

}