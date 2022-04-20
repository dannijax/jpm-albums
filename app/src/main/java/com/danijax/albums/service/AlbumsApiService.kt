package com.danijax.albums.service

import com.danijax.albums.data.model.Album
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET

interface AlbumsApiService {

    @GET("albums")
    fun getAlbums(): Flow<List<Album>>
}