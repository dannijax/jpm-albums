package com.danijax.albums.ui.albums

data class AlbumResults(val data: List<ViewAlbums>, val loading: Boolean, val message: String, val sorting: Sorting)
