package com.gokhandroid.demoecommerce.ui.order

import dagger.Module
import dagger.Provides

@Module
class OrderFragmentModule {

    @Provides
    fun bindOrdersAdapter(): OrdersAdapter {
        return OrdersAdapter()
    }
}