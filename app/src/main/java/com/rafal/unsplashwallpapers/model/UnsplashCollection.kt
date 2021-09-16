package com.rafal.unsplashwallpapers.model

data class UnsplashCollection(
    val title: String,
    val id: String,
    val cover_photo: CollectionCoverPhoto,
    val total_photos: Int
) {
    data class CollectionCoverPhoto(
        val urls: UnsplashPhotoUrls,
        val user: UnsplashUser
    )
}