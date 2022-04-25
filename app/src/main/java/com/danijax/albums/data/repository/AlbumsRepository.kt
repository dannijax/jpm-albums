package com.danijax.albums.data.repository

import android.media.MediaMetadata
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.danijax.albums.data.datasource.*
import com.danijax.albums.data.model.Album
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.ui.albums.ViewAlbums
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AlbumsRepository @Inject constructor(private val remote: RemoteSource, private val local: LocalSource) {
    private val TAG = "Repository"
    private val TIME_TO_LIVE = 1000L
    private val IN_MEM_CACHE = mutableListOf<Album>()


    fun getAlbums(): Flow<Resource<out List<Album>?>> {
        return object : NetworkBoundResource<List<Album>, List<com.danijax.albums.data.db.Album>>(){
            override fun loadFromDb(): Flow<List<Album>> {
                Timber.tag(TAG).i("Checking DB")
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

            override suspend fun saveNetworkResult(item: List<com.danijax.albums.data.db.Album>) {
                Timber.i("Saving to DB ...")
                local.save(item)
            }

            override fun fetchFromNetwork(): Flow<List<com.danijax.albums.data.db.Album>> {
                Timber.i("fetching  from remote ...")
                return remote
                    .get()
                    .map { items ->
                        convert(items)
                    }
                    .flowOn(Dispatchers.IO)
            }

            override fun isCacheResult(): Boolean {
                Timber.i("$IN_MEM_CACHE.isNotEmpty()")
                return IN_MEM_CACHE.isNotEmpty()
            }

            override fun loadFromCache(): Flow<List<Album>> {
                Timber.i("fetching  from Cache ...")
                return flow<List<Album>> {
                    IN_MEM_CACHE
                }.flowOn(Dispatchers.IO)
            }

            override fun saveToCache(resultType: List<Album>) {
                Timber.i("Saving to Cache ...")
                IN_MEM_CACHE.clear()
                IN_MEM_CACHE.addAll(resultType)
            }
        }.asFlow()
    }

    fun getOriginalList() = flow<List<Album>> {
        val data = IN_MEM_CACHE
        Timber.i("getOriginalList: " + data)
        emit(data)

    }.flowOn(Dispatchers.Main)
}