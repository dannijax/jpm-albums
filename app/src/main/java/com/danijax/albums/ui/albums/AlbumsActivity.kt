package com.danijax.albums.ui.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.danijax.albums.R
import com.danijax.albums.data.datasource.LocalSource
import com.danijax.albums.data.datasource.RemoteSource
import com.danijax.albums.data.db.AlbumsDatabase
import com.danijax.albums.data.repository.AlbumsRepository
import com.danijax.albums.service.HttpProvider

class AlbumsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        val dataSource = RemoteSource(HttpProvider)
        val database = AlbumsDatabase.getInstance(this)
        val local = LocalSource(database?.albumDao()!!)
        val repo = AlbumsRepository(dataSource, local)
        val viewModel = AlbumsViewModel(repo)
        viewModel.liveData.observe(this, Observer {
            for (x in it.data){
                Log.e("Livedata", x.title)
                Log.e("Livedata", "$x.bgColor")
            }
        })
    }
}