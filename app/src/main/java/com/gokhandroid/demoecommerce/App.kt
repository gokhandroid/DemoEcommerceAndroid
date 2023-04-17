package com.gokhandroid.demoecommerce

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.gokhandroid.demoecommerce.di.AppComponent
import com.gokhandroid.demoecommerce.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector
import java.util.*
import javax.inject.Inject

class App : DaggerApplication() {

    lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        setupDependencyInjection()
        MultiDex.install(this)
    }

    private fun setupDependencyInjection() {
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }
}