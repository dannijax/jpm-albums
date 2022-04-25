package com.danijax.albums.data.datasource

import com.danijax.albums.data.model.Album
import com.danijax.albums.service.AlbumsApiService
import com.danijax.albums.service.HttpProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteSource @Inject constructor (private val client: AlbumsApiService): DataSource<List<Album>> {
    override fun get(): Flow<List<Album>> {
        return client.getAlbums()
    }

    override fun save(data: List<Album>): Long {
        TODO("Not yet implemented")
    }
}