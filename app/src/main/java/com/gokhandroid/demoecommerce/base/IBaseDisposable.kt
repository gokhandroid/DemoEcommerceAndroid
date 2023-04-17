package com.gokhandroid.demoecommerce.base

import io.reactivex.disposables.Disposable


interface IBaseDisposable {
    fun clearDispose()
    fun applyDisposable(disposable: Disposable)
}