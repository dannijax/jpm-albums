package com.danijax.albums.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.danijax.albums.data.datasource.DataSource
import com.danijax.albums.data.model.Album
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.ui.albums.ViewAlbums
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion

class AlbumsRepository(private val remote: DataSource<List<Album>>, private val local: DataSource<List<Album>>) {

    fun getAlbums(): Flow<List<ViewAlbums>> {
        return remote.get().map {
            Mapper.toList(it)
        }.flowOn(Dispatchers.IO)
            .onCompletion {  }
    }
}