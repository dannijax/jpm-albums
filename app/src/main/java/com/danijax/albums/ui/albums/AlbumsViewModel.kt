package com.danijax.albums.ui.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.data.repository.AlbumsRepository
import kotlinx.coroutines.flow.map

class AlbumsViewModel(private val repository: AlbumsRepository): ViewModel() {

    val liveData : LiveData<AlbumResults> = repository.getAlbums()
        .map {
            val data = it.data
            AlbumResults(Mapper.toList(data!!), false, "")
        }
        .asLiveData()
}