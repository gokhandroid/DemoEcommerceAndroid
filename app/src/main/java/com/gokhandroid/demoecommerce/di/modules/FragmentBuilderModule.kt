package com.gokhandroid.demoecommerce.di.modules

import com.gokhandroid.demoecommerce.di.FragmentScope
import com.gokhandroid.demoecommerce.ui.basket.BasketFragment
import com.gokhandroid.demoecommerce.ui.basket.BasketFragmentModule
import com.gokhandroid.demoecommerce.ui.order.OrderFragmentModule
import com.gokhandroid.demoecommerce.ui.order.OrdersFragment
import com.gokhandroid.demoecommerce.ui.payment.PaymentFragment
import com.gokhandroid.demoecommerce.ui.paymentinfo.PaymentInfoFragment
import com.gokhandroid.demoecommerce.ui.product.ProductsFragment
import com.gokhandroid.demoecommerce.ui.product.ProductsFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ProductsFragmentModule::class])
    abstract fun bindProductsFragment(): ProductsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [BasketFragmentModule::class])
    abstract fun bindBasketFragment(): BasketFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [OrderFragmentModule::class])
    abstract fun bindOrdersFragment(): OrdersFragment

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun bindPaymentFragment(): PaymentFragment

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun bindPaymentInfoFragment(): PaymentInfoFragment
}