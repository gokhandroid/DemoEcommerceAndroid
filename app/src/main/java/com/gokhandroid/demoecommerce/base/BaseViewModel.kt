package com.gokhandroid.demoecommerce.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel(), IBaseDisposable {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    override fun clearDispose() {
        disposables.clear()
    }

    override fun applyDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        clearDispose()
        disposables.dispose()
    }
}