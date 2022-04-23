package com.danijax.albums.ui.albums


import android.graphics.Color
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
//import org.mockito.Mockito.verify


//@RunWith(MockitoJUnitRunner::class)
class AlbumsTest {
    @get:Rule
    //val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var albumsLiveDataObserver: Observer<AlbumResults>

    @Test
    fun fetchAlbums(){
        assert(true)
        //verify(albumsLiveDataObserver).onChanged(albumsResults)
    }
}