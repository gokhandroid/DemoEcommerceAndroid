package com.gokhandroid.demoecommerce.ui.basket

import dagger.Module
import dagger.Provides

@Module
class BasketFragmentModule {

    @Provides
    fun bindBasketAdapter(): BasketAdapter {
        return BasketAdapter()
    }
}