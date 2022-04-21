package com.danijax.albums.data.model

import android.graphics.Color
import com.danijax.albums.ui.albums.ViewAlbums
import java.util.*

object Mapper {
    private fun to(album: Album): ViewAlbums {
        val color = when((1..12).random()){
            1 -> Color.BLACK
            2 -> Color.BLUE
            3 -> Color.CYAN
            4 -> Color.GRAY
            5 -> Color.GREEN
            6 -> Color.DKGRAY
            7 -> Color.RED
            8 -> Color.TRANSPARENT
            9 -> Color.LTGRAY
            10 -> Color.MAGENTA
            11-> Color.YELLOW
            12 -> Color.WHITE
            else -> Color.WHITE
        }
        return ViewAlbums(album.userId, album.id, album.title, color);
    }

    private fun to(album: com.danijax.albums.data.db.Album): ViewAlbums {
        val color = when((1..12).random()){
            1 -> Color.BLACK
            2 -> Color.BLUE
            3 -> Color.CYAN
            4 -> Color.GRAY
            5 -> Color.GREEN
            6 -> Color.DKGRAY
            7 -> Color.RED
            8 -> Color.TRANSPARENT
            9 -> Color.LTGRAY
            10 -> Color.MAGENTA
            11-> Color.YELLOW
            12 -> Color.WHITE
            else -> Color.WHITE
        }
        return ViewAlbums(album.userId.toInt(), album.id, album.title, color);
    }

    private fun toDTO(album: com.danijax.albums.data.db.Album): Album {
        val color = when((1..12).random()){
            1 -> Color.BLACK
            2 -> Color.BLUE
            3 -> Color.CYAN
            4 -> Color.GRAY
            5 -> Color.GREEN
            6 -> Color.DKGRAY
            7 -> Color.RED
            8 -> Color.TRANSPARENT
            9 -> Color.LTGRAY
            10 -> Color.MAGENTA
            11-> Color.YELLOW
            12 -> Color.WHITE
            else -> Color.WHITE
        }
        return Album(album.userId.toInt(), album.id, album.title);
    }

    fun toList(albums: List<Album>): List<ViewAlbums>{
        return albums.map {
            to(it)
        }
    }

    fun toListFromEntity(albums: List<com.danijax.albums.data.db.Album>): List<ViewAlbums>{
        return albums.map {
            to(it)
        }
    }

    fun toDTOListFromEntity(albums: List<com.danijax.albums.data.db.Album>): List<Album>{
        return albums.map {
            toDTO(it)
        }
    }

    private fun toEntity(album: Album): com.danijax.albums.data.db.Album {
        return com.danijax.albums.data.db.Album(id = album.id, userId = "${album.userId}", title = album.title, dateModified = Date()  )
    }

    fun toEntityList(albums: List<Album>): List<com.danijax.albums.data.db.Album> {
        return albums.map { album ->
            toEntity(album)
        }
    }
}