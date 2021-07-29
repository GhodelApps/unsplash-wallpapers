package com.rafal.unsplashwallpapers.model


data class UnsplashSearchPhoto(
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val likes: Int,
    val user: UnsplashUser
)