package com.rafal.unsplashwallpapers.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rafal.unsplashwallpapers.databinding.UserViewBinding
import com.rafal.unsplashwallpapers.model.UnsplashUser

class UsersPagingAdapter :
    PagingDataAdapter<UnsplashUser, UsersPagingAdapter.UsersViewHolder>(User_Comparator) {

    class UsersViewHolder(private val binding: UserViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UnsplashUser) {
            Glide.with(itemView)
                .load(user.profile_image.medium)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.userIv)

            binding.userName.text = user.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersViewHolder {
        val binding = UserViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersPagingAdapter.UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null)
            holder.bind(item)
    }

    companion object {
        private val User_Comparator = object : DiffUtil.ItemCallback<UnsplashUser>() {
            override fun areItemsTheSame(oldItem: UnsplashUser, newItem: UnsplashUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashUser,
                newItem: UnsplashUser
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}