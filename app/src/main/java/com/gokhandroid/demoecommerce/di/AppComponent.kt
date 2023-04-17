package com.gokhandroid.demoecommerce.di

import com.gokhandroid.demoecommerce.App
import com.gokhandroid.demoecommerce.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilderModule::class, FragmentBuilderModule::class, ViewModelModule::class, RepositoryModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun build(): AppComponent
    }

    override fun inject(instance: App)
}


