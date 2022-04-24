package com.danijax.albums.ui.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.danijax.albums.data.datasource.Resource
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.data.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumsRepository): ViewModel() {

    val liveData : LiveData<AlbumResults> = repository.getAlbums()
        .map {res ->
            var result = AlbumResults(emptyList(), false, res.message!!, Sorting.ByTitle("Title"))
            res.data?.let { albums ->
                 result =  when(res){
                    Resource.Success(albums) ->  AlbumResults(Mapper.toList(albums), false, "Success", Sorting.ByTitle("Title"))
                    Resource.Loading(albums) ->  AlbumResults(Mapper.toList(albums), true, "Loading from remote", Sorting.ByTitle("Title"))
                    Resource.Error("", null) ->  AlbumResults(Mapper.toList(res.data), false, res.message, Sorting.ByTitle("Title"))
                    else -> {AlbumResults(emptyList(), false, res.message, Sorting.ByTitle("Title"))}

                }
            }
            result
        }
        .asLiveData()
}