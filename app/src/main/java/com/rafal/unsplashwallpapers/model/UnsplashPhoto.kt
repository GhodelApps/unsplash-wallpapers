package com.rafal.unsplashwallpapers.model

data class UnsplashPhoto(
    val id: String,
    val width: Int,
    val height: Int,
    val downloads: Int,
    val likes: Int,
    val user: UnsplashUser,
    val exif: UnsplashPhotoExif

) {
    data class UnsplashPhotoExif(
        val make: String,
        val model: String,
        val aperture: String,
        val focal_length: String,
        val iso: Int
    )
}