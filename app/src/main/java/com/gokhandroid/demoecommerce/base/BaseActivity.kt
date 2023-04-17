package com.gokhandroid.demoecommerce.base

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, B : ViewBinding> :
    DaggerAppCompatActivity(), LifecycleOwner {

    companion object {
        private const val FIRST_ITEM = 0
    }

    lateinit var binding: B

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Suppress("UNCHECKED_CAST")
    private val viewModelClass =
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[FIRST_ITEM] as Class<VM>

    val viewModel: VM
            by lazy {
                ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
            }

    abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun onStop() {
        super.onStop()
        (viewModel as IBaseDisposable).clearDispose()
    }
}