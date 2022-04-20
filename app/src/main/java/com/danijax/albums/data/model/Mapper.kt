package com.danijax.albums.data.model

import android.graphics.Color
import com.danijax.albums.ui.albums.ViewAlbums

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

    fun toList(albums: List<Album>): List<ViewAlbums>{
        return albums.map {
            to(it)
        }
    }
}