package com.rafal.unsplashwallpapers.view.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.rafal.unsplashwallpapers.R
import com.rafal.unsplashwallpapers.databinding.CollectionsViewBinding
import com.rafal.unsplashwallpapers.model.UnsplashCollection

class CollectionsPagingAdapter(private val listener: onCollectionClickListener) :
    PagingDataAdapter<UnsplashCollection, CollectionsPagingAdapter.CollectionsViewHolder>(
        Collections_Comparator
    ) {
    inner class CollectionsViewHolder(private val binding: CollectionsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collection: UnsplashCollection) {
            binding.apply {
                titleTv.text = collection.title
                userTv.text = collection.cover_photo.user.name
                photoCountTv.text = "${collection.total_photos} PHOTOS"
                Glide.with(itemView)
                    .load(collection.cover_photo.urls.regular)
                    .apply(
                        RequestOptions()
                            .override(Target.SIZE_ORIGINAL)
                            .transform(
                                RoundedCorners(50)
                            )
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .error(R.drawable.ic_baseline_error_24)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.pb.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.pb.visibility = View.GONE
                            return false
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.iv)

                root.setOnClickListener {
                    listener.onCollectionClick(collection.id, collection.title)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null)
            holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val binding =
            CollectionsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionsViewHolder(binding)
    }

    interface onCollectionClickListener {
        fun onCollectionClick(id: String, title: String)
    }

    companion object {
        private val Collections_Comparator = object : DiffUtil.ItemCallback<UnsplashCollection>() {
            override fun areItemsTheSame(
                oldItem: UnsplashCollection,
                newItem: UnsplashCollection
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashCollection,
                newItem: UnsplashCollection
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}