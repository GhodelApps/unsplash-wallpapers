package com.rafal.unsplashwallpapers.model

data class UnsplashUser(
    val id: String,
    val name: String,
    val profile_image: ProfileImage
) {
    data class ProfileImage(
        val small: String,
        val medium: String,
        val large: String
    )
}