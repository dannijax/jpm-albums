package com.danijax.albums.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Transaction
    @Query("SELECT * FROM albums ORDER BY id DESC")
    fun getAlbums(): Flow<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: Album) : Long

    @Update
    fun updateAlbum(album: Album): Int

    @Delete
    fun deleteAlbum(album: Album): Int
}
