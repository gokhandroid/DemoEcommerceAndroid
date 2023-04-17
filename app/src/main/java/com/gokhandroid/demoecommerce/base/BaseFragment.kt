package com.gokhandroid.demoecommerce.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, B : ViewBinding> : DaggerFragment(), IBaseFragmentView {

    companion object {
        private const val FIRST_ITEM = 0
    }

    protected lateinit var binding: B
    protected abstract fun getViewBinding(): B

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (parentFragmentManager.backStackEntryCount != 0)
                findNavController().popBackStack()
            else {
                if (!hasPopStack()) {
                    handleBackPressed()
                } else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
    }

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

    fun navigateWithAction(action: NavDirections) {
        findNavController().navigate(action)
    }

    fun navigate(resId: Int) {
        findNavController().navigate(resId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, callback)
    }

    override fun onStop() {
        super.onStop()
        (viewModel as IBaseDisposable).clearDispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    override fun hasPopStack(): Boolean {
        return true
    }

    override fun handleBackPressed() {
        callback.remove()
        requireActivity().onBackPressed()
    }
}
