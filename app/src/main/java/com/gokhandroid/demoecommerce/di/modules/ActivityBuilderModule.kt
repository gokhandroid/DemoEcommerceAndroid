package com.gokhandroid.demoecommerce.di.modules

import com.gokhandroid.demoecommerce.ui.main.MainActivity
import com.gokhandroid.demoecommerce.di.ActivityScope
import com.gokhandroid.demoecommerce.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

}