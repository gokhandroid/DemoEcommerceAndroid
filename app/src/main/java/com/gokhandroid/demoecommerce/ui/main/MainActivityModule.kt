package com.gokhandroid.demoecommerce.ui.main

import com.gokhandroid.demoecommerce.base.BaseActivity
import com.gokhandroid.demoecommerce.databinding.ActivityMainBinding
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {
    @Binds
    abstract fun bindBaseActivity(activity: MainActivity): BaseActivity<MainViewModel, ActivityMainBinding>
}