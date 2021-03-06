package com.danijax.albums.data.datasource

sealed class Resource<T>(val  data: T, val message: String?){
    data class Success<T>(val data1: T): Resource<T>(data1, "")
    data class Loading<T>(val data1: T): Resource<T>(data1, "")
    data class Error<T>(val message1: String, val data1: T): Resource<T>(data1, message1)
}
