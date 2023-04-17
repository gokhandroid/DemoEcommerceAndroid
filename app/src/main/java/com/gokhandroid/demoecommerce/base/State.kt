package com.gokhandroid.demoecommerce.base

enum class Status { SUCCESS, ERROR }

@Suppress("DataClassPrivateConstructor")
data class State private constructor(
    val status: Status,
    val error: Throwable?
) {
    companion object {
        fun success(): State {
            return State(Status.SUCCESS, null)
        }

        fun error(error: Throwable): State {
            return State(Status.ERROR, error)
        }
    }
}