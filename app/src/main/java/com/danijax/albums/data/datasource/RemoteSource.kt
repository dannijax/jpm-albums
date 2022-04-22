package com.danijax.albums.data.datasource

import com.danijax.albums.data.model.Album
import com.danijax.albums.service.HttpProvider
import kotlinx.coroutines.flow.Flow

class RemoteSource(private val client: HttpProvider): DataSource<List<Album>> {
    override fun get(): Flow<List<Album>> {
        return client.client().getAlbums()
    }

    override fun save(data: List<Album>): Long {
        TODO("Not yet implemented")
    }
}