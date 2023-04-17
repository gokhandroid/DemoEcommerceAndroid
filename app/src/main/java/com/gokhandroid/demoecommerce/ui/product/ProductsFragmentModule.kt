package com.gokhandroid.demoecommerce.ui.product

import dagger.Module
import dagger.Provides

@Module
class ProductsFragmentModule {

    @Provides
    fun bindProductsAdapter(): ProductsAdapter {
        return ProductsAdapter()
    }
}