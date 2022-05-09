package com.app.hackerearth.core

data class Resource<out T>(val status: Status, val data: T? = null, val message: Any? = null) {
    companion object{
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data)
        fun <T> error(message: Any, data: T? = null): Resource<T> =
            Resource(Status.ERROR, data, message)
        fun <T> loading(): Resource<T> = Resource(Status.LOADING)
    }
}

enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}