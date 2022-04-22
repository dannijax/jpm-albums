package com.danijax.albums.ui.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.danijax.albums.data.datasource.Resource
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.data.repository.AlbumsRepository
import kotlinx.coroutines.flow.map

class AlbumsViewModel(private val repository: AlbumsRepository): ViewModel() {

    val liveData : LiveData<AlbumResults> = repository.getAlbums()
        .map {res ->
            var result = AlbumResults(emptyList(), false, res.message!!)
            res.data?.let { albums ->
                 result =  when(res){
                    Resource.Success(albums) ->  AlbumResults(Mapper.toList(albums), false, "Success")
                    Resource.Loading(albums) ->  AlbumResults(Mapper.toList(albums), true, "Loading from remote")
                    Resource.Error("", null) ->  AlbumResults(Mapper.toList(res.data), false, res?.message!!)
                    else -> {AlbumResults(emptyList(), false, res.message!!)}
                }
            }

            result

        }
        .asLiveData()
}