package com.gokhandroid.demoecommerce.base

interface IBaseFragmentView {
    fun hasPopStack(): Boolean
    fun handleBackPressed()
}