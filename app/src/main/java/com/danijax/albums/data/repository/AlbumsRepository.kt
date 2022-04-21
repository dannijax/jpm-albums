package com.danijax.albums.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.danijax.albums.data.datasource.DataSource
import com.danijax.albums.data.datasource.NetworkBoundResource
import com.danijax.albums.data.datasource.Resource
import com.danijax.albums.data.model.Album
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.ui.albums.ViewAlbums
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import java.util.*

class AlbumsRepository(private val remote: DataSource<List<Album>>, private val local: DataSource<List<com.danijax.albums.data.db.Album>>) {
    private val TAG = "Repository"
    private val TIME_TO_LIVE = 1000L
    fun getAlbums(): Flow<Resource<out List<Album>?>> {

        return object : NetworkBoundResource<List<Album>, List<com.danijax.albums.data.db.Album>>(){
            override fun loadFromDb(): Flow<List<Album>> {
                return local.get()
                    .map { albums ->
                        Mapper.toDTOListFromEntity(albums)
                    }
            }

            override fun shouldFetch(data: List<com.danijax.albums.data.db.Album>): Boolean {
                if(data.isEmpty()){
                    return true
                }
                val item = data.first()
                val date = item.dateModified
                val now = Date()
                val interval = now.time - (date?.time ?: 0)
                return interval > TIME_TO_LIVE
            }

            override fun convert(data: List<Album>): List<com.danijax.albums.data.db.Album> {
                return Mapper.toEntityList(data)
            }

            override suspend fun saveNetworkResult(item: List<com.danijax.albums.data.db.Album>) {
                local.save(item)
            }

            override fun fetchFromNetwork(): Flow<List<com.danijax.albums.data.db.Album>> {
                return remote
                    .get()
                    .map { items ->
                       convert(items)
                    }
                    .flowOn(Dispatchers.IO)
            }


        }.asFlow()



//        return remote.get().map {
//            Mapper.toList(it)
//        }.flowOn(Dispatchers.IO)
//            .onCompletion {  }
    }
}