package com.imagefinder.features.featuresMain.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imagefinder.domain.entity.ImageItem

class ImagesAdapter : RecyclerView.Adapter<ImageItemViewHolder>() {

    private val items = mutableListOf<ImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        return ImageItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<ImageItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun add(newImageItem: ImageItem) {
        items.add(newImageItem)
        notifyItemInserted(items.size - 1)
    }
}
