package com.rafal.unsplashwallpapers.model

data class UnsplashUser(
    val name: String,
    val profile_image: ProfileImageUrls
) {
    data class ProfileImageUrls(
        val small: String,
        val medium: String,
        val large: String
    )
}