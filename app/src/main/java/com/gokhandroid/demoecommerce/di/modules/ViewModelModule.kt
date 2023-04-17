package com.gokhandroid.demoecommerce.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gokhandroid.demoecommerce.base.BaseViewModel
import com.gokhandroid.demoecommerce.ui.main.MainViewModel
import com.gokhandroid.demoecommerce.base.ViewModelFactory
import com.gokhandroid.demoecommerce.di.ViewModelKey
import com.gokhandroid.demoecommerce.ui.basket.BasketViewModel
import com.gokhandroid.demoecommerce.ui.order.OrdersViewModel
import com.gokhandroid.demoecommerce.ui.payment.PaymentViewModel
import com.gokhandroid.demoecommerce.ui.paymentinfo.PaymentInfoViewModel
import com.gokhandroid.demoecommerce.ui.product.ProductsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    abstract fun bindProductsViewModel(productsViewModel: ProductsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BasketViewModel::class)
    abstract fun bindBasketViewModel(basketViewModel: BasketViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel::class)
    abstract fun bindOrdersViewModel(basketViewModel: OrdersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentViewModel::class)
    abstract fun bindPaymentViewModel(basketViewModel: PaymentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentInfoViewModel::class)
    abstract fun bindPaymentInfoViewModel(basketViewModel: PaymentInfoViewModel): ViewModel
}