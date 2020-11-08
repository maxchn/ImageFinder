package com.imagefinder.features.featuresMain.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].id == newItems[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newItems[newItemPosition]
            }

            override fun getOldListSize() = items.size

            override fun getNewListSize() = newItems.size
        })

        items.clear()
        items.addAll(newItems)

        diff.dispatchUpdatesTo(this)
    }

    fun add(newImageItem: ImageItem) {
        items.add(newImageItem)
        notifyItemInserted(items.size - 1)
    }
}
