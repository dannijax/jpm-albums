package com.danijax.albums.ui.albums

import android.util.Log
import androidx.lifecycle.*
import com.danijax.albums.data.datasource.Resource
import com.danijax.albums.data.model.Mapper
import com.danijax.albums.data.repository.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repository: AlbumsRepository): ViewModel() {
    val sortingLiveData: MutableLiveData<AlbumResults> = MutableLiveData()
    val albumResultLiveData : LiveData<AlbumResults> =

        repository.getAlbums()
        .map {res ->
            var result = AlbumResults(emptyList(), false, res.message!!, Sorting.ByTitle("Title"))
            res.data?.let { albums ->
                 result =  when(res){
                    is Resource.Success ->  AlbumResults(Mapper.toList(albums), false, "Success", Sorting.ByTitle("Title"))
                    is Resource.Loading ->  AlbumResults(Mapper.toList(albums), true, "Loading from remote", Sorting.ByTitle("Title"))
                    is Resource.Error ->  AlbumResults(Mapper.toList(res.data), false, res.message, Sorting.ByTitle("Title"))
                }
            }

            sort(result.sorting, result)
        }
        .asLiveData()

    fun fetchAlbums(sorting: Sorting) {
        viewModelScope.launch {
            repository.getOriginalList()
                .map {
                    val res = sort(sorting, AlbumResults( Mapper.toList(it), false, "Success", sorting!!))
                    sortingLiveData.postValue(res)
                    res
                }.collect()
        }

    }

    private fun sort(sorting: Sorting, albumResults: AlbumResults): AlbumResults{
        val items = when(sorting) {
            is Sorting.ByTitle -> albumResults.data.sortedBy {
                it.title
            }
            is Sorting.ById -> albumResults.data.sortedBy {
                it.id
            }
            is Sorting.ByUserId -> albumResults.data.sortedBy {
                it.userId
            }
        }
        return AlbumResults(items, albumResults.loading, albumResults.message, sorting )
    }
}