package com.rafal.unsplashwallpapers.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "search_photo")
data class UnsplashSearchPhoto(
    @PrimaryKey val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val likes: Int,
    @Embedded val user: UnsplashUser
)