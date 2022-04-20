package com.danijax.albums.data.datasource

import kotlinx.coroutines.flow.Flow

interface DataSource<T> {
    fun get():Flow<T>
}