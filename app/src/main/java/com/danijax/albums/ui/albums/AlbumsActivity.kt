package com.danijax.albums.ui.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.danijax.albums.R
import com.danijax.albums.databinding.ActivityAlbumsBinding
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
        //viewModel.fetchAlbums()
        albumsAdapter = AlbumsAdapter(mutableListOf())
        viewModel.albumResultLiveData.observe(this, Observer { result ->
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
            binding.loadingSpinner.hide(result.loading)
            albumsAdapter.update(result.data)
            //viewModel.sorting(result)
        })

        viewModel.sortingLiveData.observe(this, Observer {items ->
            Log.e("VVM", "doing something")
            albumsAdapter.update(items.data)
        })

        //Recylcerview setup
        binding.albumsList.apply {
            layoutManager = GridLayoutManager(this@AlbumsActivity, 2)
            setHasFixedSize(true)
            adapter = albumsAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.sorting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.title -> {
                viewModel.fetchAlbums(Sorting.ByTitle(""))
                true
            }
            R.id.id -> {
                viewModel.fetchAlbums(Sorting.ById(""))
                true
            }

            R.id.userId -> {
                viewModel.fetchAlbums(Sorting.ByUserId(""))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}