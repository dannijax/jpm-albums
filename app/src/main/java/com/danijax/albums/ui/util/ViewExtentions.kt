package com.danijax.albums.ui.util

import android.opengl.Visibility
import android.view.View
object ViewExtentions {

    fun View.hide(hide:Boolean){
        visibility = if (hide) View.VISIBLE
        else View.GONE
    }
}