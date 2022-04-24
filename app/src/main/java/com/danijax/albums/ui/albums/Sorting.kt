package com.danijax.albums.ui.albums

sealed class Sorting{
    data class ByTitle(val name: String): Sorting()
    data class ById(val name: String): Sorting()
    data class ByUserId(val name: String): Sorting()
}
