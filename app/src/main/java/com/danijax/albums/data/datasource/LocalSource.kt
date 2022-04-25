package com.danijax.albums.data.datasource

import com.danijax.albums.data.db.Album
import com.danijax.albums.data.db.AlbumDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSource @Inject constructor(private val albumDao: AlbumDao): DataSource<List<Album>> {
    override fun get(): Flow<List<Album>> {
        return albumDao.getAlbums()
    }

    override fun save(data: List<Album>): Long {
        var res = 0L
        data.forEach { album ->
            res = albumDao.insertAlbum(album)
        }
        return res
    }
}