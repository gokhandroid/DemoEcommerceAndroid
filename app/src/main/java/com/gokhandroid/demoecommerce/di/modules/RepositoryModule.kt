package com.gokhandroid.demoecommerce.di.modules

import com.gokhandroid.demoecommerce.data.AppDatabase
import com.gokhandroid.demoecommerce.data.repository.*
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providerProductRepository(
        appDatabase: AppDatabase
    ): IProductRepository {
        return ProductRepository(appDatabase)
    }

    @Provides
    fun providerBasketRepository(
        appDatabase: AppDatabase
    ): IBasketRepository {
        return BasketRepository(appDatabase)
    }

    @Provides
    fun providerOrderRepository(
        appDatabase: AppDatabase
    ): IOrderRepository {
        return OrderRepository(appDatabase)
    }
}