package com.imagefinder.features.featuresMain.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imagefinder.R
import com.imagefinder.databinding.TemplateItemImageBinding
import com.imagefinder.domain.entity.ImageItem

class ImageItemViewHolder(
    private val binding: TemplateItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ImageItem) {
        binding.item = item
    }

    companion object {

        fun create(parent: ViewGroup): ImageItemViewHolder {
            val binding: TemplateItemImageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.template_item_image,
                parent,
                false
            )

            return ImageItemViewHolder(binding)
        }
    }
}
