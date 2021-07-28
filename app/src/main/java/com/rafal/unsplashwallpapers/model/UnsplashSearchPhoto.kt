package com.rafal.unsplashwallpapers.model


data class UnsplashSearchPhoto(
    val id: String,
    val description: String?,
    val urls: Urls,
    val likes: Int,
    val user: UnsplashUser
) {
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    )
}