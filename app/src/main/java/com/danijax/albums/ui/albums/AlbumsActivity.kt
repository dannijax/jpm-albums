package com.danijax.albums.ui.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.danijax.albums.data.datasource.LocalSource
import com.danijax.albums.data.datasource.RemoteSource
import com.danijax.albums.data.db.AlbumsDatabase
import com.danijax.albums.data.repository.AlbumsRepository
import com.danijax.albums.databinding.ActivityAlbumsBinding
import com.danijax.albums.service.HttpProvider
import com.danijax.albums.ui.util.ViewExtentions.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumsBinding
    private lateinit var albumsAdapter: AlbumsAdapter
    private val viewModel: AlbumsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        albumsAdapter = AlbumsAdapter(mutableListOf())
        viewModel.liveData.observe(this, Observer {
            Log.e("Live data", it.message)
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            binding.loadingSpinner.hide(it.loading)
            albumsAdapter.update(it.data)
        })

        //Recylcerview setup
        binding.albumsList.apply {
            layoutManager = GridLayoutManager(this@AlbumsActivity, 2)
            setHasFixedSize(true)
            adapter = albumsAdapter
        }
    }
}