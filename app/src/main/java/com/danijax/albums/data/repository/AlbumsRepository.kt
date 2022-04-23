package com.danijax.albums.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.danijax.albums.data.datasource.*
import com.danijax.albums.data.model.Album
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.ui.albums.ViewAlbums
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class AlbumsRepository @Inject constructor(private val remote: RemoteSource, private val local: LocalSource) {
    private val TAG = "Repository"
    private val TIME_TO_LIVE = 1000L
    fun getAlbums(): Flow<Resource<out List<Album>?>> {

        return object : NetworkBoundResource<List<Album>, List<com.danijax.albums.data.db.Album>>(){
            override fun loadFromDb(): Flow<List<Album>> {
                Log.e(TAG, "Checking DB")
                Log.e(TAG, "$")
                return local.get()
                    .map { albums ->

                        Mapper.toDTOListFromEntity(albums)

                    }.flowOn(Dispatchers.IO)

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

            override suspend fun saveNetworkResult(item: List<com.danijax.albums.data.db.Album>)
            {
                Log.e(TAG, "Saving to DB ...")
                local.save(item)
            }

            override fun fetchFromNetwork(): Flow<List<com.danijax.albums.data.db.Album>> {
                Log.e(TAG, "fetching  from remote ...")
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