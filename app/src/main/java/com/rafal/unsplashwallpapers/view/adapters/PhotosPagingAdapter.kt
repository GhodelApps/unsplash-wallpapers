package com.rafal.unsplashwallpapers.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rafal.unsplashwallpapers.databinding.PhotoViewBinding
import com.rafal.unsplashwallpapers.model.UnsplashPhoto

class PhotosPagingAdapter :
    PagingDataAdapter<UnsplashPhoto, PhotosPagingAdapter.PhotosViewHolder>(Photo_Comparator) {

    class PhotosViewHolder(private val binding: PhotoViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: UnsplashPhoto) {
            Glide.with(itemView)
                .load(photo.urls.small)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.photoIv)

            binding.photoUsername.text = photo.user.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosViewHolder {
        val binding = PhotoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null)
            holder.bind(item)
    }

    companion object {
        private val Photo_Comparator = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}