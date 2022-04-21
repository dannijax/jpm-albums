package com.danijax.albums.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danijax.albums.databinding.AlbumItemBinding

class AlbumsAdapter(private val items: MutableList<ViewAlbums>): RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
       return items.size
    }

    fun update(newItems: List<ViewAlbums>){
        this.items.clear()
        this.items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class AlbumsViewHolder(private val binding: AlbumItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(albums: ViewAlbums){
            binding.albumTitle.text = albums.title
            binding.userId.text = "${albums.userId}"
            binding.albumBg.setBackgroundColor(albums.bgColor)
        }

    }

}